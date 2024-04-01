package org.joo.libra.support.functions.mappings;

import lombok.Getter;
import org.joo.libra.support.functions.RandFunction;
import org.joo.libra.support.functions.SqrtFunction;

public class MathFunctions extends Functions {

    @Getter
    private static final MathFunctions instance;

    static {
        instance = new MathFunctions();
        instance.addFunction("sqrt", new SqrtFunction());
        instance.addFunction("rand", new RandFunction());
    }

    private MathFunctions() {
        // Nothing to do here
    }
}
