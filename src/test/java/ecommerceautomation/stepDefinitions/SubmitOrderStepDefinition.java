package ecommerceautomation.stepDefinitions;

import org.testng.asserts.SoftAssert;

import ecommerceautomation.pageobjects.CartPage;
import ecommerceautomation.pageobjects.CheckoutPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SubmitOrderStepDefinition extends StepDefinitionBase {

    @When("I add product {string} to Cart")
    public void i_add_product_to_cart_with_string(String productName) throws InterruptedException {
        refreshPageState();
        getProductCatalogue().addProductToCart(productName);
    }

    @When("^I add product (.+) to Cart$")
    public void i_add_product_to_cart(String productName) throws InterruptedException {
        refreshPageState();
        getProductCatalogue().addProductToCart(productName);
    }

    @When("^Checkout (.+) and submit the order$")
    public void checkout_and_submit_order(String productName) {
        refreshPageState();
        SoftAssert softAssert = new SoftAssert();
        CartPage cartPage = getProductCatalogue().goToCartPage();
        softAssert.assertTrue(cartPage.VerifyProductDisplay(productName), "Product not found in cart before checkout");

        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        setConfirmationPage(checkoutPage.submitOrder());
        softAssert.assertAll();
    }

    @Then("{string} message is displayed on ConfirmationPage")
    public void confirmation_message_displayed(String expectedMessage) {
        refreshPageState();
        SoftAssert softAssert = new SoftAssert();
        String actualMessage = getConfirmationPage().getConfirmationMessage();
        softAssert.assertTrue(actualMessage.equalsIgnoreCase(expectedMessage), "Confirmation page message mismatch");
        softAssert.assertAll();
    }
}
