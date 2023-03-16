package org.example.core;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserActions {

    private static final Logger log = LoggerFactory.getLogger(BrowserActions.class);
    private WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    public <T> T captureScreenshot(OutputType<T> outputType) {
        log.info("capturing screenshot .. as " + outputType.toString());
        TakesScreenshot takesScreenshot = ((TakesScreenshot) driver);
        return takesScreenshot.getScreenshotAs(outputType);
    }

    public String getPageTitle() {
        return this.driver.getTitle();
    }

}