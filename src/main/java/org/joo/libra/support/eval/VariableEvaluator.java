package org.joo.libra.support.eval;

public interface VariableEvaluator {

    Object evaluate(Object obj, String variableName) throws Exception;
}
