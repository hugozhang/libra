package org.joo.libra.support.eval;

import org.codehaus.janino.ExpressionEvaluator;

public interface ExpressionEvaluatorCache {

    ExpressionEvaluator get(EvaluationKey key);

    void put(EvaluationKey key, ExpressionEvaluator evaluator);
}
