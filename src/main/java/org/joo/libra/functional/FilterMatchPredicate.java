package org.joo.libra.functional;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.HasValue;

public class FilterMatchPredicate extends AnyMatchPredicate implements HasValue<Collection<?>> {

    private final HasValue<?> transform;

    public FilterMatchPredicate(HasValue<Object> list, String indexName, Predicate conditionPredicate,
            HasValue<?> transform) {
        super(list, indexName, conditionPredicate);
        this.transform = transform;
    }

    protected List<?> getWithCollection(Collection<?> listValue, PredicateContext context) {
        return filterWithStream(context, listValue.stream());
    }

    protected List<?> getWithArray(Object[] listValue, PredicateContext context) {
        return filterWithStream(context, Arrays.stream(listValue));
    }

    protected List<?> filterWithStream(PredicateContext context, Stream<?> stream) {
        return stream.filter(value -> test(value, context)) //
                     .map(value -> transform != null ? transform.getValue(context) : value) //
                     .collect(toList());
    }

    @Override
    public List<?> getValue(PredicateContext context) {
        Object listValue = list.getValue(context);
        if (listValue == null)
            return Collections.emptyList();
        if (listValue instanceof Object[])
            return getWithArray((Object[]) listValue, context);
        if (listValue instanceof Collection)
            return getWithCollection((Collection<?>) listValue, context);
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "FilterMatch(list = " + list + ", item = " + indexName + ", condition = " + conditionPredicate
                + ", transform = " + transform + ")";
    }
}
