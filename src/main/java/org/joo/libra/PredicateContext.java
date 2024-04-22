package org.joo.libra;

import java.util.HashMap;
import java.util.Map;

import org.joo.libra.support.eval.VariableEvaluator;
import org.joo.libra.support.eval.impl.DefaultVariableEvaluator;
import org.joo.libra.support.exceptions.PredicateValueException;
import org.joo.libra.support.functions.MultiArgsFunction;

import lombok.Getter;
import org.springframework.context.ApplicationContext;

/**
 * Represents a predicate context. This is used to evaluate the predicate.
 * Generally the context itself is used by
 * {@link org.joo.libra.sql.node.VariableExpressionNode} to resolve the
 * variable. But it can also be used by any custom predicate to hold the value
 * they need while evaluating.
 * 
 * @author griever
 *
 */
public class PredicateContext {

    private static final VariableEvaluator DEFAULT_EVALUATOR = new DefaultVariableEvaluator();

    @Getter
    private Object context;


    @Getter
    private ApplicationContext springContext;

    private final Map<String, Object> cachedValues;

    private Map<String, MultiArgsFunction> functionMappings;

    private final Map<String, Object> tempVariables = new HashMap<>();

    private VariableEvaluator evaluator = DEFAULT_EVALUATOR;

    public PredicateContext(final Object context) {
        this.context = context;
        this.cachedValues = new HashMap<>();
    }

    public PredicateContext(final Object context,ApplicationContext springContext) {
        this(context);
        this.springContext = springContext;
    }

    public PredicateContext(final Object context, Map<String, MultiArgsFunction> functionMappings) {
        this.context = context;
        this.cachedValues = new HashMap<>();
        this.functionMappings = functionMappings;
    }

    public PredicateContext(final Object context, VariableEvaluator evaluator,
            Map<String, MultiArgsFunction> functionMappings) {
        this.context = context;
        this.cachedValues = new HashMap<>();
        this.functionMappings = functionMappings;
        this.evaluator = evaluator;
    }

    public Object getVariableValue(final String name, final PredicateContext context) {
        Object value = cachedValues.get(name);
        if (value == null) {
            value = evaluateValue(name, context);
            cachedValues.put(name, value);
        }
        return value;
    }

    public Object getTempVariableValue(String name) {
        if (tempVariables.containsKey(name))
            return tempVariables.get(name);
        try {
            return DEFAULT_EVALUATOR.evaluate(tempVariables, name);
        } catch (Exception e) {
            throw new PredicateValueException(e);
        }
    }

    private Object evaluateValue(final String name, final PredicateContext context) {
        try {
            return evaluator.evaluate(context.getContext(), name);
        } catch (Exception e) {
            throw new PredicateValueException(e);
        }
    }

    public boolean hasCachedValue(final String key) {
        return cachedValues.containsKey(key);
    }

    public MultiArgsFunction getRegisteredFunction(final String name) {
        return functionMappings != null ? functionMappings.get(name) : null;
    }

    public Object getTempVariable(String name) {
        return tempVariables.get(name);
    }

    public void setTempVariable(String name, Object value) {
        tempVariables.put(name, value);
    }
}
