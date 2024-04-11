package org.joo.libra.logic;

import java.util.Arrays;

import lombok.Getter;
import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.CompositionPredicate;
import org.joo.libra.support.exceptions.PredicateExecutionException;

/**
 * Represents a predicate with <code>and</code> rule. It will be satisfied by if
 * and only if all of the child predicates are satisfied. If the child is empty,
 * it will always return true.
 * 
 * @author griever
 *
 */
@Getter
public class AndPredicate implements CompositionPredicate {

	private final Predicate[] predicates;

	public AndPredicate(final Predicate... predicates) {
		this.predicates = predicates;
	}

	@Override
	public boolean test(final PredicateContext context) throws PredicateExecutionException {
		for (Predicate predicate : predicates) {
			if (!predicate.test(context))
				return false;
		}
		return true;
	}

	public String toString() {
		String[] predicatesAsString = Arrays.stream(predicates).map(Object::toString)
				.toArray(String[]::new);
		return "AND(" + String.join(",", predicatesAsString) + ")";
	}
}
