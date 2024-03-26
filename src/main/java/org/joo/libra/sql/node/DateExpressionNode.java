package org.joo.libra.sql.node;

import org.joo.libra.Predicate;
import org.joo.libra.common.SimpleLiteralPredicate;
import org.joo.libra.support.GenericComparator;

import java.util.Date;

public class DateExpressionNode extends ValueExpressionNode<Date> {

	public DateExpressionNode() {

	}

	public DateExpressionNode(Date value) {
		this.value = value;
	}

	@Override
	public Predicate buildPredicate() {
		return new SimpleLiteralPredicate<>(value, value != null && GenericComparator.compareDate(value, null) != 0);
	}

	public String toString() {
		return value + "";
	}
}