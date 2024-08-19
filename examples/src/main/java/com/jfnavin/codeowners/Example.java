package com.jfnavin.codeowners;

import com.example.Buzz;
import com.example.Fizz;
import com.example.foo.Bar;
import com.example.foo.Foo;
import com.example.foo.a.Beta;
import com.jfnavin.codeowners.resolver.ReflectionResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Example {
    private static final Logger LOG = LogManager.getLogger(Example.class);

    public static void main(String[] args) {

        final var resolver = new ReflectionResolver();

        LOG.info("Resolved owner for Foo: {}", resolver.resolverOwner(Foo.class).orElse(null));
        LOG.info("Resolved owner for Bar: {}", resolver.resolverOwner(Bar.class).orElse(null));
        LOG.info("Resolved owner for Integer: {}", resolver.resolverOwner(Integer.class).orElse(null));

        Buzz.sayHello();
        Fizz.sayHello();
        Beta.sayHello();
        Foo.sayHello();

    }
}
