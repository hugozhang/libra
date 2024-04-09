package org.joo.libra.sql.node;

import java.util.*;
import java.util.stream.Collectors;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.common.HasValue;
import org.joo.libra.pointer.VariablePredicate;

import lombok.Data;

@Data
public class ListExpressionNode implements ExpressionNode, HasValue<Object> {

	private ListItemExpressionNode listItem;

	private Collection<HasValue<?>> rawValues;

	@Override
	public Predicate buildPredicate() {
		return new VariablePredicate(this);
	}

	@Override
	public Collection<?> getValue(final PredicateContext context) {
		if (listItem == null)
			return new ArrayList<>();
		return listItem.getValue(context);
	}

	public String toString() {
		if (listItem == null)
			return null;
		List<String> elements = listItem.getInnerNode().stream().map(Object::toString)
				.collect(Collectors.toList());
		return "LIST(" + String.join(",", elements) + ")";
	}
}