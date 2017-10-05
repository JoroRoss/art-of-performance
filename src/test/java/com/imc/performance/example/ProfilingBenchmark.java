package com.imc.performance.example;

import com.imc.performance.datamodel.Data;
import com.imc.performance.datamodel.ResultTable;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(time=500, timeUnit = TimeUnit.MILLISECONDS, iterations = 5)
public class ProfilingBenchmark {
    private ResultTable greeks;
    private Foobarator foobarator;

    @Setup
    public void setup() {
        greeks = Data.resultTable();
        foobarator = new Foobarator(new FidgetCalculator(),
                new FactorCalculator(), new FoobarCalculator(42.0, Math.PI));
    }

    @Benchmark
    @Fork(value = 1)
    @Measurement(time=500, timeUnit = TimeUnit.MILLISECONDS, iterations = 200)
    public ResultTable benchmark() {
        return foobarator.foobar(greeks);
    }
}
