package com.example;

import com.jfnavin.codeowners.annotation.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Owner("BuzzOwner")
public class Buzz {
    private static final Logger LOG = LogManager.getLogger(Buzz.class);

    public static void sayHello() {
        LOG.info("Hello from {}", Buzz.class.getName());
    }
}
