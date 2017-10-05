# The Art of Java Performance

This is the code that I use for the benchmarking and profiling demonstrations 
in my 'Art of Java Performance' talks (a.k.a. The Art of Java Tuning).

The slides are available here: 
* https://www.slideshare.net/JonathanRoss74/the-art-of-performance-tuning
* https://www.slideshare.net/JonathanRoss74/the-art-of-performance-tuning-with-presenter-notes

The code uses a couple of useful frameworks:
* Aleksey Shipilëv's JMH - http://openjdk.java.net/projects/code-tools/jmh/ - the best micro-benchmark 
framework out there
* Immutables.io - https://immutables.github.io - annotation processor for code generation of 
immutable data classes
* Guava - https://github.com/google/guava - Google Core Libraries for Java

The code is inspired by some production code surrounding a performance bottleneck I was investigating 
at IMC a couple of months ago.  Some class names have been changed to protect the innocent.

## Running the code
The code is a maven project using java 9 (but works fine if you change the source and target to 1.8).  To 
get it up and running, I would recommend running `mvn clean install` once, and then opening the project
in IntelliJ Idea.

The main workhorse benchmark for the demonstration is in `com.imc.performance.example.FoobaratorBenchmark` 
under the test sources.  Run it from within IntelliJ Idea with the JMH plugin installed 
- available here: https://github.com/artyushov/idea-jmh-plugin.  The number of iterations and forks 
should be increased to get meaningful results, it is set this low for demonstration purposes.

In the demos, I present some different techniques to optimize the code:
* Using an approximation for the atan function - change `FoobarCalculator` to use 
`ApproxAtanCalculator` instead of `NormalizedAtanCalculator` (there is also a micro-benchmark 
running those functions in isolation - see `AtanBenchmark`.)
* Pulling the `normalizedAtan` calculation out of the loop (it does not depend on a loop 
variable so is being invoked redundantly.)

If you profile the code in Java Mission Control (e.g. by running `AutoJFRBenchmark`, or by 
connecting JMH to an instance of `ProfilingBenchmark`), you discover that we are allocating 
an awful lot of data.  The worst allocation sites involve Guava `Row` objects and boxed 
`Doubles`, as well as a handful of capturing lambdas.  The following steps illustrate techniques 
to avoid that allocation pressure:
* Replacing the Guava `ForwardingTable` by a map of maps (using Guava's `ForwardingMap`)
* Using an `EnumMap` for the rows of said table
* Avoiding `Double` boxing by using a `MutableDouble` class or by switching to a specialized `EnumDoubleMap`
that maps to primitives (try Eclipse Collections for this - formerly known as GS collections).
* Avoid expensive hash code computations by 
** changing the `Option` class to use interning
** avoid redundant double indexing into the `ResultTable` - the method `ResultTable.get(i, j)` expands to 
   `ResultTable.row(i).get(j)` - the result of the row query can be extracted to a local variable and 
   reused instead.
   
Please let me know if you have any questions or encounter any difficulty running this code!  

Jonathan C. Ross - @JoroRoss - joro at imc dot com.
