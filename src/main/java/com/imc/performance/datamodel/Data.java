package com.imc.performance.datamodel;

import com.google.common.collect.ImmutableList;

import java.time.LocalDate;
import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public enum Data {
    ;

    static final Collection<LocalDate> EXPIRIES = ImmutableList.of(
            LocalDate.of(2017, 3, 17),
            LocalDate.of(2017, 4, 21),
            LocalDate.of(2017, 5, 19),
            LocalDate.of(2017, 6, 16),
            LocalDate.of(2017, 7, 21),
            LocalDate.of(2017, 9, 15),
            LocalDate.of(2017, 12, 15)
    );

    static final double[] STRIKES = DoubleStream.iterate(100, i -> i + 1)
            .limit(500)
            .toArray();

    static Collection<Option> createOptions() {
        return EXPIRIES.stream()
                .flatMap(expiry -> EnumSet.allOf(Option.Kind.class).stream().flatMap(
                        kind -> DoubleStream.of(STRIKES).mapToObj(strike -> Option.of("IMC", expiry, strike, kind))))
                .collect(Collectors.toList());
    }

    public static ResultTable resultTable() {
        ResultTable table = new ResultTable();
        Collection<Option> myOptions = createOptions();
        for (Option myOption : myOptions) {
            for (Result myResult : Result.values()) {
                table.put(myOption, myResult, 0.0);
            }
        }
        return table;
    }
}
