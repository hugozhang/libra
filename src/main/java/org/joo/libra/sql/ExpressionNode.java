package org.joo.libra.sql;

import java.math.BigDecimal;
import java.util.Map;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.HasValue;
import org.joo.libra.common.SimplePredicate;
import org.joo.libra.logic.AndPredicate;
import org.joo.libra.logic.EqualsPredicate;
import org.joo.libra.logic.NotPredicate;
import org.joo.libra.logic.OrPredicate;
import org.joo.libra.numeric.GreaterEqualPredicate;
import org.joo.libra.numeric.GreaterThanPredicate;
import org.joo.libra.numeric.LessEqualPredicate;
import org.joo.libra.numeric.LessThanPredicate;
import org.joo.libra.numeric.NumericComparator;
import org.joo.libra.sql.antlr.SqlLexer;
import org.joo.libra.text.ContainPredicate;
import org.joo.libra.text.IsEmptyPredicate;
import org.joo.libra.text.MatchPredicate;

public interface ExpressionNode {

	public Predicate buildPredicate();
	
	public ExpressionNode[] getChildren();
}

abstract class InfixExpressionNode implements ExpressionNode {
	
	private ExpressionNode left;

	private ExpressionNode right;

	public ExpressionNode getLeft() {
		return left;
	}

	public void setLeft(ExpressionNode left) {
		this.left = left;
	}

	public ExpressionNode getRight() {
		return right;
	}

	public void setRight(ExpressionNode right) {
		this.right = right;
	}
	
	@Override
	public ExpressionNode[] getChildren() {
		return new ExpressionNode[] {left, right};
	}
}

class AndExpressionNode extends InfixExpressionNode {

	@Override
	public Predicate buildPredicate() {
		return new AndPredicate(getLeft().buildPredicate(), getRight().buildPredicate());
	}
}

class OrExpressionNode extends InfixExpressionNode {

	@Override
	public Predicate buildPredicate() {
		return new OrPredicate(getLeft().buildPredicate(), getRight().buildPredicate());
	}
}

abstract class UnaryExpressionNode implements ExpressionNode {

	private ExpressionNode innerNode;

	public ExpressionNode getInnerNode() {
		return innerNode;
	}

	public void setInnerNode(ExpressionNode innerNode) {
		this.innerNode = innerNode;
	}
	
	@Override
	public ExpressionNode[] getChildren() {
		return new ExpressionNode[] {innerNode};
	}
}

class NotExpressionNode extends UnaryExpressionNode {
	
	@Override
	public Predicate buildPredicate() {
		return new NotPredicate(getInnerNode().buildPredicate());
	}
}

class ValueExpressionNode<T> implements ExpressionNode, HasValue<T> {
	
	protected T value;

	@Override
	public T getValue(PredicateContext context) {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public Predicate buildPredicate() {
		return new SimplePredicate(value != null);
	}
	
	@Override
	public ExpressionNode[] getChildren() {
		return null;
	}
}

class StringExpressionNode extends ValueExpressionNode<String> {
	
	@Override
	public Predicate buildPredicate() {
		return new SimplePredicate(value != null && !value.isEmpty());
	}
}

class NumberExpressionNode extends ValueExpressionNode<Number> {
	
	public NumberExpressionNode() {
		
	}
	
	public NumberExpressionNode(Number value) {
		this.value = value;
	}

	@Override
	public Predicate buildPredicate() {
		return new SimplePredicate(value != null && NumericComparator.compare(value, 0) != 0);
	}
}

class BooleanExpressionNode extends ValueExpressionNode<Boolean> {
	
	public BooleanExpressionNode() {
		
	}
	
	public BooleanExpressionNode(boolean value) {
		this.value = value;
	}
	
	@Override
	public Predicate buildPredicate() {
		return new SimplePredicate(Boolean.TRUE.equals(value));
	}
}

class ObjectExpressionNode extends ValueExpressionNode<Object> {
	
}

class EmptyExpressionNode extends UnaryExpressionNode {
	
	private int op;

	@Override
	public Predicate buildPredicate() {
		if (op == SqlLexer.IS_NOT_EMPTY)
			return new NotPredicate(new IsEmptyPredicate((HasValue<?>) getInnerNode()));
		return new IsEmptyPredicate((HasValue<?>) getInnerNode());
	}

	public int getOp() {
		return op;
	}

	public void setOp(int op) {
		this.op = op;
	}
}

class VariableExpressionNode implements ExpressionNode, HasValue<Object> {
	
	private String variableName;

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	@Override
	public Object getValue(PredicateContext context) {
		if (context == null) return null;
		Map<String, Object> cachedValues = context.getCachedValues();
		Object value = cachedValues.get(variableName);
		if (value == null) {
			value = getValueNoCache(context);
			cachedValues.put(variableName, value);
		}
		return value;
	}
	
	private Object getValueNoCache(PredicateContext context) {
		try {
			return ObjectUtils.getValue(context.getContext(), variableName);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Predicate buildPredicate() {
		return null;
	}
	
	@Override
	public ExpressionNode[] getChildren() {
		return null;
	}
}


abstract class AbstractBinaryOpExpressionNode<T extends HasValue<?>> implements ExpressionNode {

	private T left;
	
	private T right;
	
	private int op;

	public T getLeft() {
		return left;
	}

	public void setLeft(T left) {
		this.left = left;
	}

	public T getRight() {
		return right;
	}

	public void setRight(T right) {
		this.right = right;
	}

	public int getOp() {
		return op;
	}

	public void setOp(int op) {
		this.op = op;
	}
	
	@Override
	public ExpressionNode[] getChildren() {
		return new ExpressionNode[] {(ExpressionNode) left, (ExpressionNode) right};
	}
}

class MathExpressionNode extends AbstractBinaryOpExpressionNode<HasValue<Number>> implements HasValue<Number> {

	@Override
	public Predicate buildPredicate() {
		return null;
	}

	@Override
	public Number getValue(PredicateContext context) {
		double left = getLeft().getValue(context).doubleValue();
		double right = getRight().getValue(context).doubleValue();
		switch(getOp()) {
		case SqlLexer.PLUS:
			return left + right;
		case SqlLexer.MINUS:
			return left - right;
		case SqlLexer.TIMES:
			return new BigDecimal(left).multiply(new BigDecimal(right));
		case SqlLexer.DIVIDE:
			return new BigDecimal(left).divide(new BigDecimal(right));
		}
		return null;
	}
}

class NumericCompareExpressionNode extends AbstractBinaryOpExpressionNode<HasValue<Number>> {

	@Override
	public Predicate buildPredicate() {
		switch(getOp()) {
		case SqlLexer.GREATER_THAN:
			return new GreaterThanPredicate(getLeft(), getRight());
		case SqlLexer.GREATER_THAN_EQUALS:
			return new GreaterEqualPredicate(getLeft(), getRight());
		case SqlLexer.LESS_THAN:
			return new LessThanPredicate(getLeft(), getRight());
		case SqlLexer.LESS_THAN_EQUALS:
			return new LessEqualPredicate(getLeft(), getRight());
		}
		return null;
	}
	
}

class GenericCompareExpressionNode extends AbstractBinaryOpExpressionNode<HasValue<?>> {

	@Override
	public Predicate buildPredicate() {
		switch(getOp()) {
		case SqlLexer.EQUALS:
		case SqlLexer.IS_EQUALS:
		case SqlLexer.DBL_EQUALS:
			return new EqualsPredicate(getLeft(), getRight());
		case SqlLexer.NOT_EQUALS:
		case SqlLexer.IS_EQUALS_NOT:
			return new NotPredicate(new EqualsPredicate(getLeft(), getRight()));
		}
		return null;
	}
}

class LexicalCompareExpressionNode extends AbstractBinaryOpExpressionNode<HasValue<String>> {

	@Override
	public Predicate buildPredicate() {
		switch(getOp()) {
		case SqlLexer.MATCHES:
			return new MatchPredicate(getLeft(), getRight());
		}
		return null;
	}
}

class ContainsCompareExpressionNode extends AbstractBinaryOpExpressionNode<HasValue<?>> {

	@Override
	public Predicate buildPredicate() {
		return new ContainPredicate(getLeft(), getRight());
	}
}