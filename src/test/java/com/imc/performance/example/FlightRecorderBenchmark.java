package com.imc.performance.example;

import com.imc.performance.datamodel.Data;
import com.imc.performance.datamodel.ResultTable;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(time=500, timeUnit = TimeUnit.MILLISECONDS, iterations = 10)
public class FlightRecorderBenchmark {
    private ResultTable greeks;
    private Foobarator foobarator;

    @Setup
    public void setup() {
        greeks = Data.resultTable();
        foobarator = new Foobarator(new FidgetCalculator(),
                new FactorCalculator(), new FoobarCalculator(42.0, Math.PI));
    }

    @Benchmark
    @Fork(value = 1,
            jvmArgsAppend = {"-XX:+UnlockCommercialFeatures", "-XX:+FlightRecorder",
                    "-XX:+UnlockDiagnosticVMOptions", "-XX:+DebugNonSafepoints"})
    @Measurement(time=500, timeUnit = TimeUnit.MILLISECONDS, iterations = 200)
    public ResultTable profile() {
        return foobarator.foobar(greeks);
    }
}
