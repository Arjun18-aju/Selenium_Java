package ecommerceautomation.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import ecommerceautomation.TestComponents.BaseTest;
import ecommerceautomation.TestComponents.Retry;
import ecommerceautomation.pageobjects.CartPage;
import ecommerceautomation.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

    @Test(groups = { "ErrorHandling" }, retryAnalyzer = Retry.class)
    public void LoginErrorValidation() throws IOException, InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        landingPage.loginApplication("anshika@gmail.com", "Iamki000");
        softAssert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage(), "Login error message mismatch");
        softAssert.assertAll();
    }

    @Test
    public void ProductErrorValidation() throws IOException, InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        String productName = "ZARA COAT 3";
        ProductCatalogue productCatalogue = landingPage.loginApplication("rahulshetty@gmail.com", "Iamking@000");
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
        softAssert.assertFalse(match, "Unexpected product was found in cart");
        softAssert.assertAll();
    }
}
