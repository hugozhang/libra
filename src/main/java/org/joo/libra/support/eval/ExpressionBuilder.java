package org.joo.libra.support.eval;

public interface ExpressionBuilder {

    String build(Object obj, String root, String variableName) throws Exception;
}
