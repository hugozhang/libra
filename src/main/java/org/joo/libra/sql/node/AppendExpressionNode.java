package org.joo.libra.sql.node;

import org.joo.libra.Predicate;
import org.joo.libra.collection.AppendPredicate;
import org.joo.libra.common.HasValue;

public class AppendExpressionNode extends AbstractBinaryOpExpressionNode<HasValue<?>> {

	@Override
	public Predicate buildPredicate() {
		return new AppendPredicate(getLeft(), getRight());
	}
}