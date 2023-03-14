package org.example.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class BrowserActions {

    private static final Logger log = LogManager.getLogger(BrowserActions.class);
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