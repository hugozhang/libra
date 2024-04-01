package org.joo.libra.sql.node;

import org.joo.libra.Predicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.compare.GreaterEqualPredicate;
import org.joo.libra.compare.GreaterPredicate;
import org.joo.libra.compare.LessEqualPredicate;
import org.joo.libra.compare.LessPredicate;
import org.joo.libra.sql.antlr.SqlLexer;

public class NumericCompareExpressionNode extends AbstractBinaryOpExpressionNode<HasValue<Object>> {

	@Override
	public Predicate buildPredicate() {
		switch (getOp()) {
		case SqlLexer.GREATER_THAN:
			return new GreaterPredicate(getLeft(), getRight());
		case SqlLexer.GREATER_THAN_EQUALS:
			return new GreaterEqualPredicate(getLeft(), getRight());
		case SqlLexer.LESS_THAN:
			return new LessPredicate(getLeft(), getRight());
		case SqlLexer.LESS_THAN_EQUALS:
			return new LessEqualPredicate(getLeft(), getRight());
		default:
			return null;
		}
	}
}