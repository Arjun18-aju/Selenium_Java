package rahulshettyacademy.pageobjects;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.AbstractComponents.AbstractComponent;
import rahulshettyacademy.resources.ConfigReader;

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

    public String getErrorMessage() {
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    public RegistrationPage goToRegisterPage() {
        registerLink.click();
        return new RegistrationPage(driver);
    }

    public void goTo() {
        driver.get(ConfigReader.getUrl());
    }
}
