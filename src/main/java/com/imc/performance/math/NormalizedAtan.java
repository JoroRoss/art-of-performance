package com.imc.performance.math;

public enum NormalizedAtan {
    ;

    private static final double NORMALIZATION_FACTOR = 2 / Math.PI;

    public static double normalizedAtan(double input) {
        return Math.atan(input / NORMALIZATION_FACTOR) * NORMALIZATION_FACTOR;
    }
}
