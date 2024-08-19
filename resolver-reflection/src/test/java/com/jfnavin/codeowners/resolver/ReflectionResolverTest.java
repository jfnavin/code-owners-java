package com.jfnavin.codeowners.resolver;

import com.example.Bar;
import com.example.Foo;
import com.example.a.Buzz;
import com.jfnavin.codeowners.model.Owner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ReflectionResolverTest {

    private final ReflectionResolver resolver = new ReflectionResolver();

    public static Stream<Arguments> testParams() {
        return Stream.of(
            arguments(Foo.class, new String[] {"FooOwner", "Foo2Owner"}),
            arguments(Bar.class, new String[] {"ComExampleOwner"}),
            arguments(Buzz.class, new String[] {"ComExampleOwner"}),
            arguments(Integer.class, null)
        );
    }

    @ParameterizedTest
    @MethodSource("testParams")
    public void testResolution(final Class<?> clazz,
                               final String[] expected) {
        if (expected == null) {
            assertEquals(resolver.resolverOwner(clazz), Optional.empty());
        } else {
            assertEquals(resolver.resolverOwner(clazz), Optional.of(new Owner(expected)));
        }
    }

}