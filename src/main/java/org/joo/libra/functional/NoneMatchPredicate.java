package org.joo.libra.functional;

import java.util.Arrays;
import java.util.Collection;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.HasValue;

public class NoneMatchPredicate extends AbstractFunctionalMatchPredicate {

	public NoneMatchPredicate(HasValue<Object> list, String indexName, Predicate conditionPredicate) {
		super(list, indexName, conditionPredicate);
	}

	@Override
	protected boolean matchAsCollection(Collection<?> listValue, PredicateContext context) {
		return listValue.stream().noneMatch(value -> test(value, context));
	}

	@Override
	protected boolean matchAsArray(Object[] listValue, PredicateContext context) {
		return Arrays.stream(listValue).noneMatch(value -> test(value, context));
	}
}
