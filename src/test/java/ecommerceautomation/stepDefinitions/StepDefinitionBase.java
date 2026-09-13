package ecommerceautomation.stepDefinitions;

import java.io.IOException;

import ecommerceautomation.TestComponents.BaseTest;
import ecommerceautomation.pageobjects.ConfirmationPage;
import ecommerceautomation.pageobjects.LandingPage;
import ecommerceautomation.pageobjects.OrderPage;
import ecommerceautomation.pageobjects.ProductCatalogue;
import ecommerceautomation.pageobjects.RegistrationPage;
import ecommerceautomation.resources.ConfigReader;

public abstract class StepDefinitionBase extends BaseTest {

    protected LandingPage landingPage;
    protected ProductCatalogue productCatalogue;
    protected ConfirmationPage confirmationPage;
    protected OrderPage orderPage;
    protected RegistrationPage registrationPage;

    protected void refreshPageState() {
        landingPage = getLandingPage();
        productCatalogue = getProductCatalogue();
        confirmationPage = getConfirmationPage();
        orderPage = getOrderPage();
        registrationPage = getRegistrationPage();
    }

    protected void ensureLandingPage() throws IOException {
        if (getLandingPage() != null) {
            landingPage = getLandingPage();
            return;
        }
        landingPage = launchApplication();
        setLandingPage(landingPage);
    }

    protected String resolveProperty(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.startsWith("${") && trimmed.endsWith("}")) {
            String key = trimmed.substring(2, trimmed.length() - 1);
            String resolved = ConfigReader.getProperty(key);
            if (resolved != null) {
                return resolved;
            }
        }
        return trimmed;
    }
}
