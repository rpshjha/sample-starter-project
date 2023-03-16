package org.example.cucumber;


import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.core.BrowserActions;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter.getCurrentScenario;
import static com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter.getCurrentStep;
import static org.example.core.WebDriverInstance.*;
import static org.example.utilities.PropertyReader.get;

public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("executing scenario " + scenario.getName());
        initializeDriver(get("browser"));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    }

    @After
    public void afterScenario(Scenario scenario) {
        log.info(scenario.getName().toUpperCase() + " got " + scenario.getStatus());
        switch (scenario.getStatus()) {
            case PASSED:
                System.out.println(",------.    ,---.    ,---.    ,---.   ,------. ,------.   \n" +
                        "|  .--. '  /  O  \\  '   .-'  '   .-'  |  .---' |  .-.  \\  \n" +
                        "|  '--' | |  .-.  | `.  `-.  `.  `-.  |  `--,  |  |  \\  : \n" +
                        "|  | --'  |  | |  | .-'    | .-'    | |  `---. |  '--'  / \n" +
                        "`--'      `--' `--' `-----'  `-----'  `------' `-------'  ");
                break;
            case FAILED:
                System.out.println(",------.   ,---.   ,--. ,--.    ,------. ,------.   \n" +
                        "|  .---'  /  O  \\  |  | |  |    |  .---' |  .-.  \\  \n" +
                        "|  `--,  |  .-.  | |  | |  |    |  `--,  |  |  \\  : \n" +
                        "|  |`    |  | |  | |  | |  '--. |  `---. |  '--'  / \n" +
                        "`--'     `--' `--' `--' `-----' `------' `-------'  ");
                try {
                    FileUtils.copyFile(
                            new BrowserActions(getDriver())
                                    .captureScreenshot(OutputType.FILE),
                            new File(
                                    System.getProperty("user.dir") + File.separator +
                                            "test-output" + File.separator +
                                            "screenshots" + File.separator +
                                            scenario.getName() + System.currentTimeMillis() + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case SKIPPED:
                System.out.println(",---.   ,--. ,--. ,--. ,------.  ,------.  ,------. ,------.   \n" +
                        "'   .-'  |  .'   / |  | |  .--. ' |  .--. ' |  .---' |  .-.  \\  \n" +
                        "`.  `-.  |  .   '  |  | |  '--' | |  '--' | |  `--,  |  |  \\  : \n" +
                        ".-'    | |  |\\   \\ |  | |  | --'  |  | --'  |  `---. |  '--'  / \n" +
                        "`-----'  `--' '--' `--' `--'      `--'      `------' `-------'  \n" +
                        "                                                                ");
                break;
            default:
                break;
        }
        try {
            getCurrentScenario().assignAuthor(System.getProperty("user.name"));
            killDriver();
        } catch (java.lang.NullPointerException exception) {
            log.error(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @AfterStep
    public void afterEachStep(Scenario scenario) {
        scenario.attach(new BrowserActions(getDriver()).captureScreenshot(OutputType.BYTES), "image/png", scenario.getName());
        getCurrentStep().addScreenCaptureFromBase64String(new BrowserActions(getDriver()).captureScreenshot(OutputType.BASE64), scenario.getName());
    }
}
