package ecommerceautomation.stepDefinitions;

import org.testng.asserts.SoftAssert;

import io.cucumber.java.en.Then;

public class ErrorValidationsStepDefinition extends StepDefinitionBase {

    @Then("^\"([^\"]*)\" message is displayed$")
    public void error_message_is_displayed(String expectedMessage) {
        refreshPageState();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(expectedMessage, getLandingPage().getErrorMessage(), "Error message mismatch");
        softAssert.assertAll();
    }
}
