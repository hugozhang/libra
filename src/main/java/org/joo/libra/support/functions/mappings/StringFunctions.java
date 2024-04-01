package org.joo.libra.support.functions.mappings;

import lombok.Getter;
import org.joo.libra.support.functions.ConcatFunction;
import org.joo.libra.support.functions.LenFunction;

public class StringFunctions extends Functions {

    @Getter
    private static final StringFunctions instance;

    static {
        instance = new StringFunctions();
        instance.addFunction("len", new LenFunction());
        instance.addFunction("concat", new ConcatFunction());
    }

    private StringFunctions() {
        // Nothing to do here
    }
}
