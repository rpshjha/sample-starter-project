package org.example.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.safari.SafariDriver;

public class SafariDriverInstance {

    private static final Logger log = LogManager.getLogger(SafariDriverInstance.class);
    private SafariDriverInstance() {
    }

    public static SafariDriver createDriverUsingSafari() {
        log.info("Opening the browser : Safari");
        return new SafariDriver();
    }
}