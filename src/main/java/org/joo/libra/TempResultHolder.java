package org.joo.libra;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TempResultHolder {


    private static final ThreadLocal<List<Object>> tempResults = ThreadLocal.withInitial(ArrayList::new);

    public static List<Object> getTempResults() {
        return tempResults.get();
    }

}
