package org.joo.libra.support.functions.mappings;

import lombok.Getter;
import org.joo.libra.support.functions.AvgFunction;
import org.joo.libra.support.functions.MaxFunction;
import org.joo.libra.support.functions.MinFunction;
import org.joo.libra.support.functions.SumFunction;

public class AggregateFunctions extends Functions {

    @Getter
    private static final AggregateFunctions instance;

    static {
        instance = new AggregateFunctions();
        instance.addFunction("avg", new AvgFunction());
        instance.addFunction("sum", new SumFunction());
        instance.addFunction("min", new MinFunction());
        instance.addFunction("max", new MaxFunction());
    }

    private AggregateFunctions() {
        // Nothing to do here
    }
}
