package org.joo.libra.sql.node;

import lombok.Data;
import org.joo.libra.Predicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.text.PrintPredicate;

@Data
public class PrintExpressionNode implements ExpressionNode {

	private Object expressionNode;

	public Predicate buildPredicate() {
		if (expressionNode instanceof HasValue) {
			return new PrintPredicate((HasValue<?>)expressionNode);
		} else if (expressionNode instanceof ExpressionNode) {
			return new PrintPredicate((HasValue<?>) ((ExpressionNode)expressionNode).buildPredicate());
		} else {
			throw new IllegalArgumentException("Unsupported expression node type: " + expressionNode.getClass());
		}
	}
}