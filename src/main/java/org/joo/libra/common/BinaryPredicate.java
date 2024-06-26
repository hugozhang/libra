package org.joo.libra.common;

import org.joo.libra.PredicateContext;

/**
 * Abstract class for any predicate with 2 inputs. It wraps the evaluation with
 * following conditions:
 * 
 * <ul>
 * <li>- If both objects are null, it will return true</li>
 * <li>- If one object is null, but the other is not, it will return false</li>
 * </ul>
 * 
 * If none of the above conditions are matched, it will let the subclasses
 * determine.
 * 
 * @author griever
 *
 */
public abstract class BinaryPredicate<T, H> implements CompositionPredicate {

	private final HasValue<T> one;

	private final HasValue<H> other;

	public BinaryPredicate(final HasValue<T> one, final HasValue<H> other) {
		this.one = one;
		this.other = other;
	}

	@Override
	public boolean test(final PredicateContext context) {
		T theOne = one != null ? one.getValue(context) : null;
		H theOther = other != null ? other.getValue(context) : null;
//		if (theOne == null && theOther == null)
//			return true;
//		if (theOne == null || theOther == null)
//			return false;
		return test(theOne, theOther);
	}

	protected abstract boolean test(T one, H other);

	public String toString() {
		String name = getClass().getSimpleName().replaceAll("Predicate", "").toUpperCase();
		return name + "(" + one + "," + other + ")";
	}
}
