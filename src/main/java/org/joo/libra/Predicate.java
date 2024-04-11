package org.joo.libra;

import org.joo.libra.support.exceptions.PredicateExecutionException;

/**
 * Represents a predicate
 * 
 * @author griever
 *
 */
public interface Predicate {

    /**
     * Check if the current predicate is satisfied by given predicate context
     * 
     * @param context the context to check against. it should be also passed to
     *                child predicates if any
     * @return true if and only if the current predicate is satisfied by given
     *         predicate context
     * @throws PredicateExecutionException if any exception occurred while
     *                                     evaluating the predicate
     */
    boolean test(final PredicateContext context) throws PredicateExecutionException;
}
