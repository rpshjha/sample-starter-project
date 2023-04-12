@postcode
Feature: Validate Postcode

  Scenario: I can use the validate endpoint for validating the postcode
    Given I generate a random postcode
    When I pass the random postcode in validate endpoint
    Then I should get the result as true
