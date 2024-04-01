package org.joo.libra.support.functions.mappings;

import lombok.Getter;

public class GlobalFunctions extends Functions {

    @Getter
    private static final GlobalFunctions instance;
    
    static {
        instance = new GlobalFunctions();
        instance.addFunctions(AggregateFunctions.getInstance()) //
                .addFunctions(MathFunctions.getInstance()) //
                .addFunctions(StringFunctions.getInstance());
    }

    private GlobalFunctions() {
        // Nothing to do here
    }
}
