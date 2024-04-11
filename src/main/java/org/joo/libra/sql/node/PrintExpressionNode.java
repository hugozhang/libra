package org.joo.libra.sql.node;

import org.joo.libra.Predicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.text.PrintPredicate;

public class PrintExpressionNode extends UnaryExpressionNode {

	@Override
	public Predicate buildPredicate() {
		return new PrintPredicate((HasValue<?>) getInnerNode());
	}
}