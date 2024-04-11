package org.joo.libra.support.functions;

import org.joo.libra.PredicateContext;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JoinFunction implements MultiArgsFunction {

    @Override
    public Object apply(PredicateContext context, Object[] t) {
        if (t == null || t.length != 2)
            throw new IllegalArgumentException("join function must have at least two argument");
        Object obj = t[0];
        Object obj2 = t[1];
        if (obj == null || obj2 == null)
            return null;
        if (!(obj instanceof Collection)) {
            throw new IllegalArgumentException("join fist argument must list");
        }
        if (!(obj2 instanceof String)) {
            throw new IllegalArgumentException("join second argument must be string");
        }

        Function<Object, String> propertyExtractor = new PropertyExtractor((String)obj2);

        return ((Collection<Object>)obj).stream()
                .map(propertyExtractor)
                .collect(Collectors.joining(","));

    }
}
