package org.example.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static org.example.core.ChromeDriverInstance.createDriverUsingChrome;
import static org.example.core.FirefoxDriverInstance.createDriverUsingFirefox;
import static org.example.core.SafariDriverInstance.createDriverUsingSafari;
import static org.example.utilities.PropertyReader.get;

public class WebDriverInstance {
    private static final Logger log = LoggerFactory.getLogger(WebDriverInstance.class);
    private static final ThreadLocal<WebDriver> wDriver = new ThreadLocal<>();

    private WebDriverInstance() {
    }

    /**
     * Gets the browser driver.
     *
     * @param browserName the browser
     * @return the browser driver
     */
    private static WebDriver getBrowserDriver(String browserName) {
        WebDriver driver = null;

        if (browserName == null)
            browserName = "chrome";

        switch (browserName) {
            case "firefox":
                driver = createDriverUsingFirefox();
                break;
            case "chrome":
                driver = createDriverUsingChrome();
                break;
            case "safari":
                driver = createDriverUsingSafari();
                break;
            default:
                log.error("Browser name provided is not correct..!!");
                break;
        }

        assert driver != null;
        if (Boolean.parseBoolean(get("isWindowMax")))
            driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(get("page.load.timeout"))));

        return driver;
    }

    public static void initializeDriver(final String browser) {
        log.info("initializing the web-driver");
        wDriver.set(getBrowserDriver(browser));
    }

    /**
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        if (wDriver.get() == null)
            throw new RuntimeException("webDriver not initialized.please initialize the driver by calling initializeDriver(final String browser) method");

        return wDriver.get();
    }


    /**
     * kills the driver
     */
    public static void killDriver() {
        if (getDriver() != null) {
            log.info("closing the browser");
            getDriver().quit();
        }
        wDriver.remove();
    }
}