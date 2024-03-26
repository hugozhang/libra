package org.joo.libra.date;

import org.joo.libra.common.BinaryPredicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.support.GenericComparator;

import java.util.Date;

/**
 * Represents a numeric <code>greater than</code> predicate
 * 
 * @author griever
 *
 */
public class GreaterPredicate extends BinaryPredicate<Date, Date> {

	public GreaterPredicate(final HasValue<Date> one, final HasValue<Date> other) {
		super(one, other);
	}

	@Override
	protected boolean doSatisifiedBy(final Date one, final Date other) {
		return GenericComparator.compareDate(one, other) > 0;
	}
}
