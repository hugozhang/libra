package org.joo.libra.text;

import org.joo.libra.LiteralPredicate;
import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.CompositionPredicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.support.exceptions.PredicateExecutionException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Represents a <code>is empty</code> predicate. It supports
 * <code>String</code>, <code>Collection</code> and <code>Array</code>. Also if
 * the value is null, it will always return true regardless of the type.
 * 
 * @author griever
 *
 */
public class SlicePredicate implements Predicate, LiteralPredicate<Object>, HasValue<Object> {

	private final int start;

	private final int end;

	private final HasValue<?> value;

	private Object literalValue;

	public SlicePredicate(final HasValue<?> value,int start,int end) {
		this.value = value;
		this.start = start;
		this.end = end;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean test(final PredicateContext context) {
		Object rawValue = value.getValue(context);
		if (rawValue == null)
			return true;
		if (rawValue instanceof String) {
			String rawString = (String)rawValue;
			int len = rawString.length();
			this.literalValue = rawString.substring(max(0,min(start,len)), min(len, end));
			return rawValue.toString().isEmpty();
		}
		if (rawValue instanceof List) {
			List rawList =(List) rawValue;
			int len = rawList.size();
			this.literalValue = rawList.subList(max(0,min(start,len)), min(len, end));
			return ((List) rawValue).isEmpty();
		} if (rawValue instanceof Object[]) {
			Object[] rawArray= (Object[]) rawValue;
			int len = rawArray.length;
			this.literalValue = Arrays.copyOfRange(rawArray, max(0, min(start,len)), min(len, end));
			return ((Object[]) rawValue).length == 0;
		}
		return false;
	}

	public String toString() {
		return "SLICE(" + value + ")";
	}

	@Override
	public Object calculateLiteralValue(PredicateContext context) throws PredicateExecutionException {
		return literalValue;
	}

	@Override
	public Object getValue(PredicateContext context) {
		test(context);
		return literalValue;
	}
}
