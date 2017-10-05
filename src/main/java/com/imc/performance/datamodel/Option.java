package com.imc.performance.datamodel;

import org.immutables.value.Value;

import java.time.LocalDate;

@Value.Style(allParameters = true)
@Value.Immutable
public interface Option {
    String getSymbol();

    LocalDate getExpiry();

    double getStrike();

    Kind getKind();

    enum Kind {
        CALL,
        PUT
    }

    static Option of(String symbol, LocalDate expiry, double strike, Kind kind) {
        return ImmutableOption.of(symbol, expiry, strike, kind);
    }
}
