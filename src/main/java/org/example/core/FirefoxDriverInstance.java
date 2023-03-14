package org.example.core;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverInstance {

    private static final Logger log = LogManager.getLogger(FirefoxDriverInstance.class);
    private FirefoxDriverInstance() {
    }

    public static FirefoxDriver createDriverUsingFirefox() {
        log.info("Opening the browser : Firefox");
        return new FirefoxDriver();
    }
}