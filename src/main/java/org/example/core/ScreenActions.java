package org.example.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.example.utilities.PropertyReader.get;
public class ScreenActions {

    private static final Logger log = LoggerFactory.getLogger(ScreenActions.class);
    private final WebDriverWait wait;

    public ScreenActions(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(get("default.timeout"))));
    }

    public void click(By locator) {
        this.wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void enterText(By locator, String text) {
        this.wait.until(ExpectedConditions.elementToBeClickable(locator)).sendKeys(text);
    }

    public String getText(By locator) {
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public String getText(By locator, long timeout) {
        return this.wait
                .withTimeout(Duration.ofSeconds(timeout))
                .ignoring(TimeoutException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.title")))
                .getText();
    }

    public boolean isDisplayed(By locator) {
        try {
            return this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}