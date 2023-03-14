package org.example.web;

import org.example.core.WebDriverInstance;
import org.testng.annotations.Test;

public class TestCodeForWebdriver {

    @Test
    void launchBrowser(){


        WebDriverInstance.initializeDriver("chrome");


        WebDriverInstance.getDriver().get("https://www.google.com");
    }
}
