package ecommerceautomation.stepDefinitions;

import io.cucumber.java.en.Given;

import java.io.IOException;

public class CommonStepDefinitions extends StepDefinitionBase {

    @Given("I landed on Ecommerce Page")
    public void i_landed_on_ecommerce_page() throws Exception {
        ensureLandingPage();
        refreshPageState();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_with_username_and_password(String username, String password) throws IOException {
        ensureLandingPage();
        refreshPageState();
        setProductCatalogue(landingPage.loginApplication(resolveProperty(username), resolveProperty(password)));
        refreshPageState();
    }
}
