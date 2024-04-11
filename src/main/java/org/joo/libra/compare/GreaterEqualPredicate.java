package org.joo.libra.compare;

import org.joo.libra.common.BinaryPredicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.support.GenericComparator;

/**
 * Represents a numeric <code>greater than or equals to</code> predicate
 * 
 * @author griever
 *
 */
public class GreaterEqualPredicate extends BinaryPredicate<Object, Object> {

	public GreaterEqualPredicate(final HasValue<Object> one, final HasValue<Object> other) {
		super(one, other);
	}

	@Override
	protected boolean test(final Object one, final Object other) {
		return GenericComparator.compare(one, other) >= 0;
	}
}
