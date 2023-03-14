package org.example.core;

import lombok.extern.java.Log;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

@Log
public class BrowserActions {

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