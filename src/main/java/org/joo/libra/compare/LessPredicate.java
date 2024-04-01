package org.joo.libra.compare;

import org.joo.libra.common.BinaryPredicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.support.GenericComparator;

/**
 * Represents a numeric <code>less than</code> predicate
 * 
 * @author griever
 *
 */
public class LessPredicate extends BinaryPredicate<Object, Object> {

	public LessPredicate(final HasValue<Object> one, final HasValue<Object> other) {
		super(one, other);
	}

	@Override
	protected boolean doSatisifiedBy(final Object one, final Object other) {
		return GenericComparator.compare(one, other) < 0;
	}
}
