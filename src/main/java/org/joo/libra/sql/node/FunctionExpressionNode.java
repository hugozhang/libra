package org.joo.libra.sql.node;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.HasList;
import org.joo.libra.common.HasValue;
import org.joo.libra.pointer.VariablePredicate;
import org.joo.libra.support.functions.MultiArgsFunction;
import org.joo.libra.support.functions.mappings.GlobalFunctions;

import lombok.Data;

@Data
public class FunctionExpressionNode implements ExpressionNode, HasValue<Object> {

	private String name;

	private HasList inner;

	@Override
	public Predicate buildPredicate() {
		return new VariablePredicate(this);
	}

	@Override
	public Object getValue(final PredicateContext context) {
		MultiArgsFunction func = getFunc(context);
		Object[] args = getArgs(context);
		return func.apply(context, args);
	}

	private MultiArgsFunction getFunc(final PredicateContext context) {
		MultiArgsFunction func = context != null ? context.getRegisteredFunction(name) : null;
		if (func != null)
			return func;
		func = GlobalFunctions.getInstance().getRegisteredFunction(name);
		if (func == null)
			throw new IllegalArgumentException("Function " + name + " is not defined");
		return func;
	}

	public Object[] getArgs(final PredicateContext context) {
		if (inner == null)
			return new Object[0];
		return inner.getValueAsArray(context);
	}

	public String toString() {
		return name + "(" + inner + ")";
	}
}