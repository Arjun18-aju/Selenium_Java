package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;
import rahulshettyacademy.pageobjects.RegistrationPage;
import rahulshettyacademy.resources.ConfigReader;

public class StepDefinitionImpl extends BaseTest {

    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public ConfirmationPage confirmationPage;
    public OrderPage orderPage;
    public RegistrationPage registrationPage;

    @Given("I landed on Ecommerce Page")
    public void I_landed_on_Ecommerce_Page() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_username_and_password(String username, String password) {
        productCatalogue = landingPage.loginApplication(resolveProperty(username), resolveProperty(password));
    }

    @When("^I add product (.+) to Cart$")
    public void i_add_product_to_cart(String productName) throws InterruptedException {
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
    }

    @When("^Checkout (.+) and submit the order$")
    public void checkout_submit_order(String productName) {
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductDisplay(productName);
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        confirmationPage = checkoutPage.submitOrder();
    }

    @Then("{string} message is displayed on ConfirmationPage")
    public void message_displayed_confirmationPage(String string) {
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
        driver.close();
    }

    @Then("^\"([^\"]*)\" message is displayed$")
    public void something_message_is_displayed(String strArg1) throws Throwable {
        Assert.assertEquals(strArg1, landingPage.getErrorMessage());
        driver.close();
    }

    @Then("user should be redirected to the product catalogue")
    public void user_should_be_redirected_to_the_product_catalogue() {
        Assert.assertTrue(productCatalogue.getProductList().size() > 0);
    }

    @Then("^cart should display (.+)$")
    public void cart_should_display(String productName) {
        CartPage cartPage = productCatalogue.goToCartPage();
        Assert.assertTrue(cartPage.VerifyProductDisplay(productName));
    }

    @Given("^I open my orders page$")
    public void i_open_my_orders_page() {
        orderPage = productCatalogue.goToOrdersPage();
    }

    @Then("^order history should contain (.+)$")
    public void order_history_should_contain(String productName) {
        Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
    }

    @Given("^I am on registration page$")
    public void i_am_on_registration_page() throws IOException {
        if (landingPage == null) {
            landingPage = launchApplication();
        }
        registrationPage = landingPage.goToRegisterPage();
    }

    @When("^I fill the registration form with first name (.+), last name (.+), email (.+), phone (.+), occupation (.+), gender (.+), and password (.+)$")
    public void i_fill_the_registration_form_with_valid_data(String firstName, String lastName, String email,
                                                            String phone, String occupation, String gender, String password) {
        registrationPage.registerUser(firstName, lastName, email, phone, occupation, gender, password);
    }

    @Then("^I should see the registration page loaded$")
    public void i_should_see_the_registration_page_loaded() {
        Assert.assertTrue(driver.getPageSource().contains("Register"));
    }

    private String resolveProperty(String value) {
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