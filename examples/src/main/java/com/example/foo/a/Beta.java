package com.example.foo.a;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Beta {
    private static final Logger LOG = LogManager.getLogger(Beta.class);

    public static void sayHello() {
        LOG.info("Hello from {}", Beta.class.getName());
    }
}
