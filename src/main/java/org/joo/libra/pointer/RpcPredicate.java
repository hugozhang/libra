package org.joo.libra.pointer;

import org.joo.libra.LiteralPredicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.CompositionPredicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.support.exceptions.PredicateExecutionException;

import java.util.Collection;

/**
 *
 */
public class RpcPredicate implements CompositionPredicate, LiteralPredicate<Object> {

	private final HasValue<Object> value;

	public RpcPredicate(HasValue<Object> value) {
		this.value = value;
	}

	@Override
	public boolean test(PredicateContext context) {
		return true;
	}

	public String toString() {
		return "RPC(" + value + ")";
	}

	@Override
	public Object calculateLiteralValue(PredicateContext context) throws PredicateExecutionException {
		return value.getValue(context);
	}
}
