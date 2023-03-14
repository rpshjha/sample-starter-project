package org.example.core;

import lombok.extern.java.Log;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Log
public class ChromeDriverInstance {

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