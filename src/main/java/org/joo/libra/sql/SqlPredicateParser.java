package org.joo.libra.sql;

import org.joo.libra.Predicate;
import org.joo.libra.PredicateContext;
import org.joo.libra.support.exceptions.MalformedSyntaxException;

public interface SqlPredicateParser {

	Predicate parse(String predicate, PredicateContext context) throws MalformedSyntaxException;
}
