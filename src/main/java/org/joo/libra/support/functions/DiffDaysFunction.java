package org.joo.libra.support.functions;

import org.joo.libra.PredicateContext;

import java.time.*;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiffDaysFunction implements MultiArgsFunction {

    @Override
    public Object apply(PredicateContext context, Object[] t) {
        if (t == null || t.length != 2)
            throw new IllegalArgumentException("DiffDays function must have at least two argument");
        Object obj = t[0];
        Object obj2 = t[1];
        if (obj == null || obj2 == null)
            throw new IllegalArgumentException("DiffDays argument must Date and not null");
        if (!(obj instanceof Date)) {
            throw new IllegalArgumentException("DiffDays fist argument must Date");
        }
        if (!(obj2 instanceof Date)) {
            throw new IllegalArgumentException("DiffDays second argument must be Date");
        }
        return Period.between(convert((Date)obj),convert((Date)obj2)).getDays();

    }

    public static LocalDate convert(Date date) {
        // 1. 将 Date 转换为 Instant
        Instant instant = date.toInstant();

        // 2. 将 Instant 在系统默认时区（ZoneId.systemDefault()）下转换为 ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

        // 3. 从 ZonedDateTime 中提取 LocalDate

        return zonedDateTime.toLocalDate();
    }
}
