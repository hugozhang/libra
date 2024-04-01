package org.joo.libra.sql.node;

import org.joo.libra.Predicate;
import org.joo.libra.common.SimpleLiteralPredicate;
import org.joo.libra.support.GenericComparator;

public class NumberExpressionNode extends ValueExpressionNode<Object> {

	public NumberExpressionNode() {

	}

	public NumberExpressionNode(final Object value) {
		this.value = value;
	}

	@Override
	public Predicate buildPredicate() {
		return new SimpleLiteralPredicate<>(value, value != null && GenericComparator.compare(value, 0) != 0);
	}

	public String toString() {
		return value + "";
	}
}