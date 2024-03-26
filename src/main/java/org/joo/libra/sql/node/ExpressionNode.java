package org.joo.libra.sql.node;

import org.joo.libra.Predicate;

public interface ExpressionNode {

	Predicate buildPredicate();
}