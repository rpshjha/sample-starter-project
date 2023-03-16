package org.example.web;

import org.example.core.WebDriverInstance;
import org.junit.jupiter.api.Test;


public class TestCodeForWebdriver {

    @Test
    void launchBrowser(){


        WebDriverInstance.initializeDriver("chrome");


        WebDriverInstance.getDriver().get("https://www.google.com");
    }
}
