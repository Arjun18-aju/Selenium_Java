package ecommerceautomation.stepDefinitions;

import java.io.IOException;

import org.testng.asserts.SoftAssert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegistrationStepDefinition extends StepDefinitionBase {

    @Given("^I am on registration page$")
    public void i_am_on_registration_page() throws IOException {
        ensureLandingPage();
        refreshPageState();
        setRegistrationPage(getLandingPage().goToRegisterPage());
        refreshPageState();
    }

    @When("^I fill the registration form with first name (.+), last name (.+), email (.+), phone (.+), occupation (.+), gender (.+), and password (.+)$")
    public void i_fill_the_registration_form_with_valid_data(String firstName, String lastName, String email,
                                                           String phone, String occupation, String gender, String password) {
        refreshPageState();
        getRegistrationPage().registerUser(firstName, lastName, email, phone, occupation, gender, password);
    }

    @Then("^I should see the registration page loaded$")
    public void i_should_see_the_registration_page_loaded() {
        refreshPageState();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getDriver().getPageSource().contains("Register"), "Registration page content not loaded");
        softAssert.assertAll();
    }
}
