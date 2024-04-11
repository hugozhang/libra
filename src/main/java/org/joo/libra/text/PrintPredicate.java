package org.joo.libra.text;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.CompositionPredicate;
import org.joo.libra.common.HasValue;

import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * Represents a <code>is empty</code> predicate. It supports
 * <code>String</code>, <code>Collection</code> and <code>Array</code>. Also if
 * the value is null, it will always return true regardless of the type.
 * 
 * @author griever
 *
 */
@Slf4j
public class PrintPredicate implements CompositionPredicate {

	private final HasValue<?> value;

	public PrintPredicate(final HasValue<?> value) {
		this.value = value;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean test(final PredicateContext context) {
		Object rawValue = value.getValue(context);
		if (rawValue instanceof Collection) {
			log.debug("[log] print Collection: " + Joiner.on(",").join(Lists.newArrayList(((Collection) rawValue).toArray())));
		} else if (rawValue instanceof Object[]) {
			log.debug("[log] print Object[]: " + Joiner.on(",").join((Object[]) rawValue));
		} else {
			log.debug("[log] print : " + rawValue);
		}
		return true;
	}

	public String toString() {
		return "PRINT(" + value + ")";
	}
}
