package org.joo.libra.sql.node;


import lombok.Data;
import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.HasValue;
import org.joo.libra.logic.AndPredicate;

import java.util.Map;

@Data
public class MapExpressionNode implements ExpressionNode, HasValue<Object> {

    private VariableExpressionNode left;

    private VariableExpressionNode right;

    @Override
    public Predicate buildPredicate() {
        return new AndPredicate(left.buildPredicate(), right.buildPredicate());
    }

    @Override
    public Object getValue(PredicateContext context) {
        Object mapValue = left.getValue(context);
        if (mapValue instanceof Map)
            return ((Map<?,?>) mapValue).get(right.getValue(context));
        throw new IllegalArgumentException("Left value is not a map");
    }
}
