package org.joo.libra.sql.node;

import org.joo.libra.Predicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.date.GreaterEqualPredicate;
import org.joo.libra.date.GreaterPredicate;
import org.joo.libra.date.LessEqualPredicate;
import org.joo.libra.date.LessPredicate;
import org.joo.libra.sql.antlr.SqlLexer;

import java.util.Date;

public class DateCompareExpressionNode extends AbstractBinaryOpExpressionNode<HasValue<Date>> {

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