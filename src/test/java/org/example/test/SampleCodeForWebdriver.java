package org.example.test;

import org.example.core.WebDriverInstance;
import org.testng.annotations.Test;

public class SampleCodeForWebdriver {

    @Test
    void launchBrowser(){


        WebDriverInstance.initializeDriver("chrome");


        WebDriverInstance.getDriver().get("https://www.google.com");
    }
}
