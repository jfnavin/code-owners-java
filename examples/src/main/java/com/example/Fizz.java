package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Fizz {
    private static final Logger LOG = LogManager.getLogger(Fizz.class);

    public static void sayHello() {
        LOG.info("Hello from {}", Fizz.class.getName());
    }
}
