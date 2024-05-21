package org.joo.libra.text;

import java.util.Arrays;
import java.util.Collection;

import org.joo.libra.common.BinaryPredicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.sql.antlr.SqlParser;
import org.joo.libra.support.ListUtils;

/**
 * Represents a general <code>contains</code> predicate. It supports
 * <code>String</code>, <code>Collection</code> or <code>Array</code>.
 * 
 * @author griever
 *
 */
@SuppressWarnings("rawtypes")
public class ContainPredicate extends BinaryPredicate {

    private int op;

    @SuppressWarnings("unchecked")
    public ContainPredicate(final HasValue<?> one, final HasValue<?> other,int op) {
        super(one, other);
        this.op = op;
    }

    @Override
    protected boolean test(final Object one, final Object other) {
        if (one instanceof String) {
            if (op == SqlParser.CONTAINS) {
                return one.toString().contains(other.toString());
            } else if (op == SqlParser.STARTS_WITH) {
                return one.toString().startsWith(other.toString());
            } else if (op == SqlParser.ENDS_WITH) {
                return one.toString().endsWith(other.toString());
            }
        }
        if (one instanceof Collection<?>)
            return ListUtils.contains((Collection<?>) one, other);
        if (one instanceof Object[])
            return ListUtils.contains(Arrays.asList((Object[]) one), other);
        return false;
    }
}
