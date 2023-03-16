package org.example.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.safari.SafariDriver;

public class SafariDriverInstance {
    private static final Logger log = LoggerFactory.getLogger(SafariDriverInstance.class);
    private SafariDriverInstance() {
    }

    public static SafariDriver createDriverUsingSafari() {
        log.info("Opening the browser : Safari");
        return new SafariDriver();
    }
}