package org.joo.libra.sql.node;

import lombok.Getter;
import lombok.Setter;
import org.joo.libra.Predicate;
import org.joo.libra.common.HasValue;
import org.joo.libra.text.SlicePredicate;

@Getter
@Setter
public class SliceExpressionNode extends UnaryExpressionNode {

    private int start;

    private int end;

    @Override
    public Predicate buildPredicate() {
        return new SlicePredicate((HasValue<Object>)getInnerNode(),start,end);
    }
}
