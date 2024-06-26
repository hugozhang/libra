package org.joo.libra.logic;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.CompositionPredicate;
import org.joo.libra.support.exceptions.PredicateExecutionException;

/**
 * Represents a predicate with <code>not</code> rule. It will be satisfied by if
 * and only if the child predicate is not satisfied.
 * 
 * @author griever
 *
 */
public class NotPredicate implements CompositionPredicate {

	private final Predicate predicate;

	public NotPredicate(final Predicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean test(final PredicateContext context) throws PredicateExecutionException {
		return !predicate.test(context);
	}

	public String toString() {
		return "NOT(" + predicate.toString() + ")";
	}
}
