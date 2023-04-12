package org.example.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Assert;

public class MyStepdefs {

    private ResponseBody randomPostcode;
    private ResponseBody validatePostcode;

    @Given("I generate a random postcode")
    public void iGenerateARandomPostcode() {

        ExtractableResponse<Response> extract = RestAssured
                .given()
                .log()
                .all()
                .get("https://api.postcodes.io/random/postcodes")
                .then().log().all().and().extract();

        randomPostcode = extract.response();

        Assert.assertTrue(extract.statusCode() == 200);
    }

    @When("I pass the random postcode in validate endpoint")
    public void iPassTheRandomPostcodeInValidateEndpoint() {

        String postcode = randomPostcode.jsonPath().get("result.postcode");

        ExtractableResponse<Response> extract = RestAssured.given()
                .log()
                .all()
                .get("https://postcodes.io/postcodes/" + postcode + "/validate")
                .then().log().all().and().extract();

        validatePostcode = extract.response();

        Assert.assertTrue(extract.statusCode() == 200);
    }

    @Then("I should get the result as true")
    public void iShouldGetTheResultAsTrue() {

        String value = validatePostcode.jsonPath().getString("result");

        Assert.assertTrue(value.equals("true"));
    }
}

