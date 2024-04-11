package org.joo.libra.functional;

import org.joo.libra.LiteralPredicate;
import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.TempResultHolder;
import org.joo.libra.common.HasValue;
import org.joo.libra.sql.node.VariableExpressionNode;
import org.joo.libra.support.exceptions.PredicateExecutionException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EveryMatchPredicate extends AbstractFunctionalMatchPredicate implements LiteralPredicate<Object> {

    public EveryMatchPredicate(HasValue<Object> list, String indexName, Predicate conditionPredicate) {
        super(list, indexName, conditionPredicate);
    }

    @Override
    protected boolean matchAsCollection(Collection<?> listValue, PredicateContext context) {
       return listValue.stream().noneMatch(value -> {
           boolean isSatisfied = test(value, context);
           if (isSatisfied) {
               TempResultHolder.getTempResults().add(value);
           }
           return !isSatisfied;
       });
    }

    @Override
    protected boolean matchAsArray(Object[] listValue, PredicateContext context) {
        return Arrays.stream(listValue).noneMatch(value -> {
            boolean isSatisfied = test(value, context);
            if (isSatisfied) {
                TempResultHolder.getTempResults().add(value);
            }
            return !isSatisfied;
        });
    }

    @Override
    public Object calculateLiteralValue(PredicateContext context) throws PredicateExecutionException {
        if (list instanceof VariableExpressionNode) {
            VariableExpressionNode variableExpressionNode = (VariableExpressionNode) list;
            return context.getTempVariableValue("$" + variableExpressionNode.getVariableName());
        }
        return null;
    }
}
