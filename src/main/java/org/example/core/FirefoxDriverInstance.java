package org.example.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverInstance {

    private static final Logger log = LoggerFactory.getLogger(FirefoxDriverInstance.class);

    private FirefoxDriverInstance() {
    }

    public static FirefoxDriver createDriverUsingFirefox() {
        log.info("Opening the browser : Firefox");
        return new FirefoxDriver();
    }
}