package org.joo.libra.sql.optimizers;

import org.joo.libra.sql.node.ExpressionNode;

import lombok.Getter;

@Getter
public class OptimizeStatus {

	private final ExpressionNode node;

	private final int changes;

	public OptimizeStatus(final ExpressionNode node, final int changes) {
		this.node = node;
		this.changes = changes;
	}
}
