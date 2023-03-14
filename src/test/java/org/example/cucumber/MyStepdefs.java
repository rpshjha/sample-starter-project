package org.example.cucumber;

import io.cucumber.java.en.Given;
import org.example.core.WebDriverInstance;

public class MyStepdefs {

    @Given("I navigate to {string}")
    public void i_navigate_to(String string) {
        // Write code here that turns the phrase above into concrete actions
        WebDriverInstance.getDriver().get(string);
    }
}

