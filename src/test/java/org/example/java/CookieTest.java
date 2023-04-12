package org.example.java;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CookieTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeAll
    void setup() {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);

        driver.manage().window().maximize();
        driver.get("https://kpmg.com/xx/en/home.html");

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

    @Test
    void selectedCookieShouldBeSaved() {

        System.out.println("cookie setting before..\n");
        for (Cookie temp : driver.manage().getCookies()) {
            System.out.println(temp + "\nname :" + temp.getName());
        }

        WebElement manageChoiceCookieBtn = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'cookie-setting-link')][text()='Manage Choices']")));
        manageChoiceCookieBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Functional Cookies']"))).click();

        WebElement checkboxFunctionalCookie = wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//h4[text()='Functional Cookies']/following::input[1]"))));

        if (checkboxFunctionalCookie.getAttribute("aria-checked").equals("false")) {
            Actions actions = new Actions(driver);
            actions.moveToElement(checkboxFunctionalCookie).click().build().perform();
            wait.until(ExpectedConditions.attributeContains(By.xpath("//h4[text()='Functional Cookies']/following::input[1]"), "aria-checked", "true"));
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#accept-recommended-btn-handler"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Go to India (English)']"))).click();

        System.out.println("cookie setting after..\n");
        for (Cookie temp : driver.manage().getCookies()) {
            System.out.println(temp + "\nname :" + temp.getName());
        }

    }

    @AfterAll
    void tearDown() {

//        driver.quit();

    }
}
