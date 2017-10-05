package com.imc.performance.example;


import com.imc.performance.datamodel.Result;
import com.imc.performance.datamodel.ResultTable;
import com.imc.performance.datamodel.Option;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Foobarator {
    private final FidgetCalculator fidgetCalculator;
    private final FactorCalculator factorCalculator;
    private final FoobarCalculator foobarCalculator;

    Foobarator(FidgetCalculator fidgetCalculator,
            FactorCalculator factorCalculator,
            FoobarCalculator foobarCalculator) {
        this.fidgetCalculator = fidgetCalculator;
        this.factorCalculator = factorCalculator;
        this.foobarCalculator = foobarCalculator;
    }

    ResultTable foobar(ResultTable table) {
        double spot = getSpot(table);
        Map<LocalDate, Double> fidgetByExpiry = fidgetCalculator.fidgetByExpiry(table);
        Map<LocalDate, List<Option>> optionsByExpiry = table.rowKeySet().stream()
                .collect(Collectors.groupingBy(Option::getExpiry));

        for (Map.Entry<LocalDate, Double> entry : fidgetByExpiry.entrySet()) {
            double factor = factorCalculator.computeFactor(entry.getValue(), spot);
            List<Option> options = optionsByExpiry.get(entry.getKey());

            for (Option option : options) {
                double fidget = table.get(option, Result.TOTAL_FIDGET);
                double adjusted = foobarCalculator.calculate(fidget, entry.getValue(), factor);
                table.put(option, Result.FOOBAR, adjusted);
            }
        }
        return table;
    }

    private static double getSpot(ResultTable aPositionEntries) {
        return aPositionEntries.column(Result.SPOT).values().stream()
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
