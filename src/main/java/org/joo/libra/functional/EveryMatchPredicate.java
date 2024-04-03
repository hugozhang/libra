package org.joo.libra.functional;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.TempResultHolder;
import org.joo.libra.common.HasValue;
import org.joo.libra.sql.node.VariableExpressionNode;

import java.util.Arrays;
import java.util.Collection;

public class EveryMatchPredicate extends AbstractFunctionalMatchPredicate {

    public EveryMatchPredicate(HasValue<Object> list, String indexName, Predicate conditionPredicate) {
        super(list, indexName, conditionPredicate);
    }

    @Override
    protected boolean satisfiesAsCollection(Collection<?> listValue, PredicateContext context) {
        boolean b = listValue.stream().filter(value -> {
            boolean isSatisfied = satisfiedBy(value, context);
            if (isSatisfied) {
                TempResultHolder.getTempResults().add(value);
            }
            return isSatisfied;
        }).count() != listValue.size();
        VariableExpressionNode variableExpressionNode = (VariableExpressionNode) list;
        context.setTempVariable("$" + variableExpressionNode.getVariableName(), TempResultHolder.getTempResults());
        return b;
    }

    @Override
    protected boolean satisfiesAsArray(Object[] listValue, PredicateContext context) {
        boolean b = Arrays.stream(listValue).filter(value -> {
            boolean isSatisfied = satisfiedBy(value, context);
            if (isSatisfied) {
                TempResultHolder.getTempResults().add(value);
            }
            return isSatisfied;
        }).count() != listValue.length;
        VariableExpressionNode variableExpressionNode = (VariableExpressionNode) list;
        context.setTempVariable("$" + variableExpressionNode.getVariableName(), TempResultHolder.getTempResults());
        return b;
    }
}
