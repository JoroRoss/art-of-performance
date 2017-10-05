package com.imc.performance.example;

import com.imc.performance.datamodel.Result;
import com.imc.performance.datamodel.ResultTable;

import java.time.LocalDate;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

class FidgetCalculator {
    Map<LocalDate, Double> fidgetByExpiry(ResultTable input) {
        return input.rowMap().entrySet().stream()
                .collect(groupingBy(entry -> entry.getKey().getExpiry(),
                        summingDouble(entry -> entry.getValue().get(Result.TOTAL_FIDGET))));
    }
}
