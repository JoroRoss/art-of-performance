package com.imc.performance.math;

public enum ApproximateAtan {
    ;

    /**
     * Compute a (normalized) approximation of the arctangent, accurate to within around 0.1 degrees (~3e-3 radians).
     * This is based on a well-known rational approximation:
     * <pre>
     *        arctan(x) ~= pi/2 * ( bx + x^2 ) / ( 1 + 2bx + x^2),
     * </pre>
     * <p>with b=0.596227.
     */



    public static double normalizedAtan(double x) {
        return x >= 0.0 ? normalizedAtanFirstQuadrant(x) : -normalizedAtanFirstQuadrant(-x);
    }

    private static double normalizedAtanFirstQuadrant(double x) {
        double x2 = x * x;
        double bx = 0.3795698970194059 * x;
        double sum = bx + x2;
        return sum / (0.4052847345693511 + bx + sum);
    }
}
