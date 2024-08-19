package com.jfnavin.codeowners.benchmark;

import com.example.Buzz;
import com.example.Fizz;
import com.example.foo.Bar;
import com.example.foo.Foo;
import com.example.foo.a.Alpha;
import com.example.foo.a.Beta;
import com.jfnavin.codeowners.resolver.OwnerResolver;
import com.jfnavin.codeowners.resolver.ReflectionResolver;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.xml.stream.events.Characters;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

@State(Scope.Thread)
public class ResolverBenchmarks {

    private static final Class<?>[] CLASSES = {
            Fizz.class,
            Buzz.class,
            Foo.class,
            Bar.class,
            Alpha.class,
            Beta.class,
            Integer.class,
            ArrayList.class,
            Characters.class
    };

    private final OwnerResolver resolverWithoutCaching = new ReflectionResolver(0);
    private final OwnerResolver resolverWithCaching = new ReflectionResolver();
    private final OwnerResolver resolverWithLimitedCaching = new ReflectionResolver(5);

    @Benchmark
    public void reflectionWithoutCaching(final Blackhole bh) {
        bh.consume(resolverWithoutCaching.resolverOwner(selectClass()));
    }

    @Benchmark
    public void reflectionWithCaching(final Blackhole bh) {
        bh.consume(resolverWithCaching.resolverOwner(selectClass()));
    }

    @Benchmark
    public void reflectionWithLimitedCaching(final Blackhole bh) {
        bh.consume(resolverWithLimitedCaching.resolverOwner(selectClass()));
    }

    private Class<?> selectClass() {
        return CLASSES[RandomGenerator.getDefault().nextInt(CLASSES.length)];
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ResolverBenchmarks.class.getSimpleName())
                .forks(1)
                .warmupIterations(2)
                .measurementIterations(3)
                .build();

        new Runner(opt).run();
    }
}
