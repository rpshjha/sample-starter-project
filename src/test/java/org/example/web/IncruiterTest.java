package org.example.web;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.core.WebDriverInstance;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static org.example.core.WebDriverInstance.getDriver;
import static org.example.web.IncruiterTest.ScheduleFor.THIRTY;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IncruiterTest {

    WebDriverWait wait;

    String username = "rpshfsn@gmail.com";
    String password = "Admin@123";

    @BeforeAll
    void setup() {
        WebDriverInstance.initializeDriver("chrome");
        getDriver().get("https://incruiter.com/interviewer/login");

        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
    }

    @AfterAll
    void tearDown() {
        getDriver().quit();
    }

    @Test
    void addInterviewSlot() throws InterruptedException {
        login(username, password);
        openCalendarAndSelect(THIRTY);
        deleteExistingSlot();
        addSlots();
        saveChanges();
        Thread.sleep(5000);
    }

    private void openCalendarAndSelect(ScheduleFor days) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-target='#interviewer_schedule_modal'] i"))).click();

        switch (days) {
            case SEVEN:
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='schedule-pill active'][@data-id='week']"))).click();
                break;
            case THIRTY:
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='schedule-pill active'][@data-id='month']"))).click();
                break;
            default:
                System.out.println("invalid..!");
        }
    }

    private static void verifyLoginApi(String username, String password) {
        Response loginResponse = RestAssured.given()
                .formParam("email", username)
                .formParam("password", password)
                .post("https://incserve.incruiter.com/api/interviewer/login")
                .then().extract().response();
        Assertions.assertEquals(200, loginResponse.statusCode());
        System.out.println(loginResponse.getBody().prettyPrint());
    }

    private static void verifyGetTimeSlotApi() {
        Response getSlotResponse = RestAssured.given()
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .when()
                .body("_token=EsuIzeJpKgPQntxFR2rHhcEurYBxL6IEnp24aMr5&dates%5B%5D=2023-3-28&dates%5B%5D=2023-3-29&dates%5B%5D=2023-3-30&dates%5B%5D=2023-3-31&dates%5B%5D=2023-4-1&dates%5B%5D=2023-4-2&dates%5B%5D=2023-4-3&dates%5B%5D=2023-4-4&dates%5B%5D=2023-4-5&dates%5B%5D=2023-4-6&dates%5B%5D=2023-4-7&dates%5B%5D=2023-4-8&dates%5B%5D=2023-4-9&dates%5B%5D=2023-4-10&dates%5B%5D=2023-4-11&dates%5B%5D=2023-4-12&dates%5B%5D=2023-4-13&dates%5B%5D=2023-4-14&dates%5B%5D=2023-4-15&dates%5B%5D=2023-4-16&dates%5B%5D=2023-4-17&dates%5B%5D=2023-4-18&dates%5B%5D=2023-4-19&duration=month")
                .post("https://incserve.incruiter.com/account/interviewer/get-timeslots")
                .then().extract().response();
        Assertions.assertEquals(200, getSlotResponse.statusCode());
        System.out.println(getSlotResponse.getBody().prettyPrint());
    }

    private void saveChanges() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Save Changes']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[text()='My Schedule']/following-sibling::button[@aria-label='Close'][@class='close']/span"))).click();
    }

    private void addSlots() throws InterruptedException {
        for (int fromTime = 7; fromTime < 22; fromTime = fromTime + 2) {
            String time = getTime(fromTime);
            scheduleInterviewSlot(time);
            Thread.sleep(400);
        }
    }

    void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='email']"))).sendKeys(username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']"))).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Sign In']"))).click();

        wait.until(ExpectedConditions.urlContains("/dashboard"));

        verifyLoginApi(username, password);
    }

    private void deleteExistingSlot() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignore) {
        }
        By btnDelete = By.cssSelector("i.del-slot-btn");

        try {
            while (wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(btnDelete)).size() > 0) {
                wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(btnDelete));
                getDriver().findElement(btnDelete).click();
            }
        } catch (org.openqa.selenium.TimeoutException ignore) {
        }
    }

    void scheduleInterviewSlot(String fromTime) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.add-slot-btn"))).click();
        selectSlot(fromTime);
    }

    void selectSlot(String fromTime) {
        LocalTime start = LocalTime.parse(fromTime);
        LocalTime end = start.plusHours(1);
        System.out.println("selecting slot from " + fromTime + " to " + end);

        List<WebElement> from = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("select[name='from_time[]']")));

        Select selectFrom = new Select(from.get(from.size() - 1));
        selectFrom.selectByVisibleText(fromTime);

        List<WebElement> to = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("select[name='to_time[]']")));

        Select selectTo = new Select(to.get(to.size() - 1));
        selectTo.selectByVisibleText(end.toString());
    }

    static String getTime(int t) {
        if (t < 10) return "0" + t + ":00";
        else return t + ":00";
    }

    enum ScheduleFor {
        SEVEN,
        THIRTY
    }

}
