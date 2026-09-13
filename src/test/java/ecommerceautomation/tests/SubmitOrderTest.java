package ecommerceautomation.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import ecommerceautomation.TestComponents.BaseTest;
import ecommerceautomation.pageobjects.CartPage;
import ecommerceautomation.pageobjects.CheckoutPage;
import ecommerceautomation.pageobjects.ConfirmationPage;
import ecommerceautomation.pageobjects.OrderPage;
import ecommerceautomation.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {
    private final String productName = "ZARA COAT 3";

    @Test(dataProvider = "getData", groups = { "Purchase" })
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
        productCatalogue.addProductToCart(input.get("product"));

        CartPage cartPage = productCatalogue.goToCartPage();
        softAssert.assertTrue(cartPage.VerifyProductDisplay(input.get("product")), "Product not displayed in cart");

        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();

        String confirmMessage = confirmationPage.getConfirmationMessage();
        softAssert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."), "Order confirmation message mismatch");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = { "submitOrder" })
    public void OrderHistoryTest() {
        SoftAssert softAssert = new SoftAssert();
        ProductCatalogue productCatalogue = landingPage.loginApplication("anshika@gmail.com", "Iamking@000");
        OrderPage ordersPage = productCatalogue.goToOrdersPage();
        softAssert.assertTrue(ordersPage.VerifyOrderDisplay(productName), "Order history does not contain the expected product");
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//ecommerceautomation//data//PurchaseOrder.json");
        return new Object[][] { { data.get(0) }, { data.get(1) } };
    }
}