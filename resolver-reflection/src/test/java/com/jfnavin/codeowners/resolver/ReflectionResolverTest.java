package com.jfnavin.codeowners.resolver;

import com.example.Bar;
import com.example.Foo;
import com.example.a.Buzz;
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
            arguments(Foo.class, "FooOwner"),
            arguments(Bar.class, "ComExampleOwner"),
            arguments(Buzz.class, "ComExampleOwner"),
            arguments(Integer.class, null)
        );
    }

    @ParameterizedTest
    @MethodSource("testParams")
    public void testResolution(final Class<?> clazz,
                               final String expected) {
        assertEquals(resolver.resolverOwner(clazz), Optional.ofNullable(expected));
    }

}