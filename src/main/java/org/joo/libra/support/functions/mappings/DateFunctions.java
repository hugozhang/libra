package org.joo.libra.support.functions.mappings;

import lombok.Getter;
import org.joo.libra.support.functions.DiffDaysFunction;
import org.joo.libra.support.functions.JoinFunction;

public class DateFunctions extends Functions {

    @Getter
    private static final DateFunctions instance;

    static {
        instance = new DateFunctions();
        instance.addFunction("diffDays", new DiffDaysFunction());
    }

    private DateFunctions() {
        // Nothing to do here
    }
}
