package org.joo.libra.support.functions.mappings;

import lombok.Getter;
import org.joo.libra.support.functions.ConcatFunction;
import org.joo.libra.support.functions.JoinFunction;
import org.joo.libra.support.functions.LenFunction;

public class ListFunctions extends Functions {

    @Getter
    private static final ListFunctions instance;

    static {
        instance = new ListFunctions();
        instance.addFunction("join", new JoinFunction());
    }

    private ListFunctions() {
        // Nothing to do here
    }
}
