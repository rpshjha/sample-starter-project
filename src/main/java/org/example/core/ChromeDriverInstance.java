package org.example.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverInstance {

    private static final Logger log = LogManager.getLogger(ChromeDriverInstance.class);

    private ChromeDriverInstance() {
    }

    public static ChromeDriver createDriverUsingChrome() {
        log.info("Opening the browser : Chrome");

        System.setProperty("webdriver.http.factory", "jdk-http-client");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        return new ChromeDriver();
    }
}