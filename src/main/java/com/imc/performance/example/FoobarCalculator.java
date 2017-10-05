package com.imc.performance.example;

import com.imc.performance.math.NormalizedAtan;

class FoobarCalculator {

    private final double firstFactor;
    private final double anotherFactor;

    FoobarCalculator(double firstFactor, double anotherFactor) {
        this.firstFactor = firstFactor;
        this.anotherFactor = anotherFactor;
    }

    double calculate(double delta, double totalDelta, double factor) {
        double scale = firstFactor * factor;
        return delta * scale * NormalizedAtan.normalizedAtan(totalDelta * anotherFactor / scale);
    }
}
