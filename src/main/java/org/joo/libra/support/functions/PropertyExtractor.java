package org.joo.libra.support.functions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class PropertyExtractor  implements Function<Object, String> {

    private final String propertyName;

    public PropertyExtractor(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String apply(Object bean) {
        try {
            Method getterMethod = bean.getClass().getMethod("get" + capitalize(propertyName));
            Object value = getterMethod.invoke(bean);
            return String.valueOf(value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to extract property '" + propertyName + "' from object", e);
        }
    }

    private String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

}
