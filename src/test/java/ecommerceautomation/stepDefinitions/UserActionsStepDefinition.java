package ecommerceautomation.stepDefinitions;

import org.testng.asserts.SoftAssert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class UserActionsStepDefinition extends StepDefinitionBase {

    @Then("user should be redirected to the product catalogue")
    public void user_should_be_redirected_to_the_product_catalogue() {
        refreshPageState();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getProductCatalogue().getProductList().size() > 0, "Product catalogue is empty");
        softAssert.assertAll();
    }

    @Then("^cart should display (.+)$")
    public void cart_should_display(String productName) {
        refreshPageState();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getProductCatalogue().goToCartPage().VerifyProductDisplay(productName), "Cart does not display the expected product");
        softAssert.assertAll();
    }

    @Given("^I open my orders page$")
    public void i_open_my_orders_page() {
        refreshPageState();
        setOrderPage(getProductCatalogue().goToOrdersPage());
    }

    @Then("^order history should contain (.+)$")
    public void order_history_should_contain(String productName) {
        refreshPageState();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getOrderPage().VerifyOrderDisplay(productName), "Order history missing expected product");
        softAssert.assertAll();
    }
}
