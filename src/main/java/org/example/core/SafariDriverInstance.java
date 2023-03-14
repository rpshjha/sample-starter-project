package org.example.core;

import lombok.extern.java.Log;
import org.openqa.selenium.safari.SafariDriver;

@Log
public class SafariDriverInstance {

    private SafariDriverInstance() {
    }

    public static SafariDriver createDriverUsingSafari() {
        log.info("Opening the browser : Safari");
        return new SafariDriver();
    }
}