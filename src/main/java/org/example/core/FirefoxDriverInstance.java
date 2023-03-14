package org.example.core;


import lombok.extern.java.Log;
import org.openqa.selenium.firefox.FirefoxDriver;

@Log
public class FirefoxDriverInstance {

    private FirefoxDriverInstance() {
    }

    public static FirefoxDriver createDriverUsingFirefox() {
        log.info("Opening the browser : Firefox");
        return new FirefoxDriver();
    }
}