package ecommerceautomation.pageobjects;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ecommerceautomation.AbstractComponents.AbstractComponent;
import ecommerceautomation.resources.ConfigReader;

public class LandingPage extends AbstractComponent {

    private final WebDriver driver;

    @FindBy(id = "userEmail")
    private WebElement userEmail;

    @FindBy(id = "userPassword")
    private WebElement passwordEle;

    @FindBy(id = "login")
    private WebElement submit;

    @FindBy(css = "a[routerlink='/auth/register']")
    private WebElement registerLink;

    @FindBy(css = "[class*='flyInOut']")
    private WebElement errorMessage;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProductCatalogue loginApplication(String email, String password) {
        installLoginErrorCapture();
        userEmail.sendKeys(email);
        passwordEle.sendKeys(password);
        submit.click();

        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.alertIsPresent());
            if (alert != null) {
                alert.accept();
            }
        } catch (Exception e) {
            // No JavaScript alert present; Chrome password prompt is suppressed in browser options.
        }

        return new ProductCatalogue(driver);
    }

    private void installLoginErrorCapture() {
        ((JavascriptExecutor) driver).executeScript(
                "if (!window.__loginErrorCaptureInstalled) { " +
                        "window.__loginErrorCaptureInstalled = true; " +
                        "var originalOpen = XMLHttpRequest.prototype.open; " +
                        "var originalSend = XMLHttpRequest.prototype.send; " +
                        "XMLHttpRequest.prototype.open = function(method, url, async, user, pass) { this.__loginUrl = url; return originalOpen.apply(this, arguments); }; " +
                        "XMLHttpRequest.prototype.send = function(body) { var self = this; this.addEventListener('readystatechange', function() { " +
                        "if (self.readyState === 4 && typeof self.__loginUrl === 'string' && self.__loginUrl.indexOf('/api/ecom/auth/login') !== -1) { " +
                        "try { var payload = JSON.parse(self.responseText || '{}'); if (payload && payload.message) { window.__loginErrorMessage = payload.message; } } " +
                        "catch (error) { if (self.responseText && self.responseText.trim()) { window.__loginErrorMessage = self.responseText.trim(); } } " +
                        "} " +
                        "}); return originalSend.apply(this, arguments); }; " +
                        "window.__loginErrorMessage = ''; " +
                        "} " +
                        "window.__loginErrorMessage = '';"
        );
    }

    public String getErrorMessage() {
        String[] selectors = {"[class*='flyInOut']", ".toast-message", "[role='alert']", ".alert-danger", ".error-message"};
        for (String selector : selectors) {
            try {
                WebElement message = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
                return message.getText();
            } catch (Exception ignored) {
                // Try the next candidate selector for the login error toast/message.
            }
        }

        try {
            String networkMessage = (String) ((JavascriptExecutor) driver)
                    .executeScript("return (window.__loginErrorMessage && window.__loginErrorMessage.trim()) || null;");
            if (networkMessage != null && !networkMessage.trim().isEmpty()) {
                return networkMessage.trim();
            }
        } catch (Exception ignored) {
            // The live app may return the validation payload through the login API instead of rendering it in the DOM.
        }

        throw new NoSuchElementException("No login error message element was found on the page.");
    }

    public RegistrationPage goToRegisterPage() {
        registerLink.click();
        return new RegistrationPage(driver);
    }

    public void goTo() {
        driver.get(ConfigReader.getUrl());
    }
}
