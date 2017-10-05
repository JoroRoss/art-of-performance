package com.imc.performance.example;

import com.imc.performance.math.ApproximateAtan;
import com.imc.performance.math.NormalizedAtan;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@Warmup(time=500, timeUnit = TimeUnit.MILLISECONDS, iterations = 5)
@Measurement(time=500, timeUnit = TimeUnit.MILLISECONDS, iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(1)
public class AtanBenchmark {
    @Benchmark
    public double atan() {
        return NormalizedAtan.normalizedAtan(1.0);
    }

    @Benchmark
    public double approximate() {
        return ApproximateAtan.normalizedAtan(1.0);
    }
}
