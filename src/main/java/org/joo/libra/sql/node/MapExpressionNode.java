package org.joo.libra.sql.node;


import lombok.Data;
import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.HasValue;
import org.joo.libra.map.MapPredicate;

import java.util.Map;

@Data
public class MapExpressionNode implements ExpressionNode, HasValue<Object> {

    private HasValue<Object> left;

    private HasValue<Object> right;

    @Override
    public Predicate buildPredicate() {
        return new MapPredicate(left, right);
    }

    @Override
    public Object getValue(PredicateContext context) {
        Object objValue = left.getValue(context);
        if (objValue instanceof Map) {
            Map<?, ?> mapValue = (Map<?, ?>) objValue;
            return (mapValue).get(right.getValue(context));
        }
        throw new IllegalArgumentException("Left value is not a map");
    }
}
