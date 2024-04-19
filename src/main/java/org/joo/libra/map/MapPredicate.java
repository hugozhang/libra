package org.joo.libra.map;

import org.joo.libra.LiteralPredicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.BinaryPredicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.support.exceptions.PredicateExecutionException;

import java.util.Collection;
import java.util.Map;

/**
 * A predicate to check if one map object has a key. It
 * will use Java built-in <code>map</code> method of
 * {@link Collection}
 * 
 * @author griever
 *
 */
@SuppressWarnings("rawtypes")
public class MapPredicate extends BinaryPredicate implements LiteralPredicate<Object> {

    private Object oneValue;

    private Object otherValue;

    @SuppressWarnings("unchecked")
    public MapPredicate(final HasValue<?> one, final HasValue<?> other) {
        super(one, other);
    }

    @Override
    protected boolean test(final Object one, final Object other) {
        this.oneValue = one;
        this.otherValue = other;
        if (one instanceof Map)
            return ((Map) one).containsKey(other);
        return false;
    }

    @Override
    public Object calculateLiteralValue(PredicateContext context) throws PredicateExecutionException {
        return ((Map) oneValue).get(otherValue);
    }
}
