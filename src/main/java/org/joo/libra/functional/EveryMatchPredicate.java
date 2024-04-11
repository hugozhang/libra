package org.joo.libra.functional;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.TempResultHolder;
import org.joo.libra.common.HasValue;
import org.joo.libra.sql.node.VariableExpressionNode;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EveryMatchPredicate extends AbstractFunctionalMatchPredicate {

    public EveryMatchPredicate(HasValue<Object> list, String indexName, Predicate conditionPredicate) {
        super(list, indexName, conditionPredicate);
    }

    @Override
    protected boolean matchAsCollection(Collection<?> listValue, PredicateContext context) {
        List<?> collected = listValue.stream().filter(value -> {
            boolean isSatisfied = test(value, context);
            if (isSatisfied) {
                TempResultHolder.getTempResults().add(value);
            }
            return isSatisfied;
        }).collect(Collectors.toList());
        VariableExpressionNode variableExpressionNode = (VariableExpressionNode) list;
        context.setTempVariable("$" + variableExpressionNode.getVariableName(), TempResultHolder.getTempResults());
        return !collected.isEmpty();
    }

    @Override
    protected boolean matchAsArray(Object[] listValue, PredicateContext context) {
        boolean b = Arrays.stream(listValue).filter(value -> {
            boolean isSatisfied = test(value, context);
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
