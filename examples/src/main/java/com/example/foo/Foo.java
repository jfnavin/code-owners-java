package com.example.foo;

import com.jfnavin.codeowners.annotation.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Owner({"FooOwner", "Foo2Owner"})
public class Foo {
    private static final Logger LOG = LogManager.getLogger(Foo.class);

    public static void sayHello() {
        LOG.info("Hello from {}", Foo.class.getName());
    }
}
