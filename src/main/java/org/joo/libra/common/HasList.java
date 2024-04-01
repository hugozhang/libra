package org.joo.libra.common;

import java.util.Collection;

import org.joo.libra.PredicateContext;

/**
 * Represents anything which can have a value
 * 
 * @author griever
 */
public interface HasList extends HasValue<Collection<?>> {

    /**
     * Get the value this object is holding using a given context
     * 
     * @param context the context
     * @return the value this object is currently holding
     */
    Object[] getValueAsArray(final PredicateContext context);
}
