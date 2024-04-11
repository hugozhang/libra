package org.joo.libra.functional;

import java.util.Collection;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.HasValue;
import org.joo.libra.support.exceptions.PredicateExecutionException;

public abstract class AbstractFunctionalMatchPredicate implements Predicate {

    protected HasValue<Object> list;
    protected String indexName;
    protected Predicate conditionPredicate;

    public AbstractFunctionalMatchPredicate(HasValue<Object> list, String indexName, Predicate conditionPredicate) {
        this.list = list;
        this.indexName = indexName;
        this.conditionPredicate = conditionPredicate;
    }

    @Override
    public boolean test(PredicateContext context) throws PredicateExecutionException {
        Object listValue = list.getValue(context);
        if (listValue == null)
            return matchAsArray(new Object[0], context);
        if (listValue instanceof Object[])
            return matchAsArray((Object[]) listValue, context);
        if (listValue instanceof Collection)
            return matchAsCollection((Collection<?>) listValue, context);
        return false;
    }

    protected abstract boolean matchAsCollection(Collection<?> listValue, PredicateContext context);

    protected abstract boolean matchAsArray(Object[] listValue, PredicateContext context);

    protected boolean test(Object value, PredicateContext context) {
        context.setTempVariable(indexName, value);
        return conditionPredicate.test(context);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().replace("Predicate", "") + "(list = " + list + ", item = " + indexName
                + ", condition = " + conditionPredicate + ")";
    }
}
