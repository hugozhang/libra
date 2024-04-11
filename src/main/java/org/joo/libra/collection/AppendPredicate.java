package org.joo.libra.collection;

import org.joo.libra.common.BinaryPredicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.support.ListUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * A predicate to check if one object is contained by another collection. It
 * will use Java built-in <code>append</code> method of
 * {@link Collection}
 * 
 * @author griever
 *
 */
@SuppressWarnings("rawtypes")
public class AppendPredicate extends BinaryPredicate {

    @SuppressWarnings("unchecked")
    public AppendPredicate(final HasValue<?> one, final HasValue<?> other) {
        super(one, other);
    }

    @Override
    protected boolean doSatisifiedBy(final Object one, final Object other) {
        if (one instanceof Collection)
            return ((Collection) one).add(other);
        return false;
    }
}
