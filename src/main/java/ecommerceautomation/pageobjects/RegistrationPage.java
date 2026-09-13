package ecommerceautomation.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import ecommerceautomation.AbstractComponents.AbstractComponent;

public class RegistrationPage extends AbstractComponent {

    WebDriver driver;

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "userEmail")
    private WebElement userEmail;

    @FindBy(id = "userMobile")
    private WebElement userMobile;

    @FindBy(css = "select[formcontrolname='occupation']")
    private WebElement occupation;

    @FindBy(css = "input[value='Male']")
    private WebElement maleGender;

    @FindBy(css = "input[value='Female']")
    private WebElement femaleGender;

    @FindBy(id = "userPassword")
    private WebElement userPassword;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPassword;

    @FindBy(css = "input[formcontrolname='required']")
    private WebElement adultCheckBox;

    @FindBy(css = "input#login[type='submit']")
    private WebElement registerButton;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterFirstName(String firstNameText) {
        firstName.clear();
        firstName.sendKeys(firstNameText);
    }

    public void enterLastName(String lastNameText) {
        lastName.clear();
        lastName.sendKeys(lastNameText);
    }

    public void enterEmail(String email) {
        userEmail.clear();
        userEmail.sendKeys(email);
    }

    public void enterMobile(String mobile) {
        userMobile.clear();
        userMobile.sendKeys(mobile);
    }

    public void selectOccupation(String occupationName) {
        Select select = new Select(occupation);
        select.selectByVisibleText(occupationName);
    }

    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            maleGender.click();
        } else if (gender.equalsIgnoreCase("female")) {
            femaleGender.click();
        }
    }

    public void enterPassword(String password) {
        userPassword.clear();
        userPassword.sendKeys(password);
        confirmPassword.clear();
        confirmPassword.sendKeys(password);
    }

    public void markAdult() {
        if (!adultCheckBox.isSelected()) {
            adultCheckBox.click();
        }
    }

    public void submitRegistration() {
        registerButton.click();
    }

    public void registerUser(String firstNameText, String lastNameText, String email, String mobile, String occupationName, String gender, String password) {
        enterFirstName(firstNameText);
        enterLastName(lastNameText);
        enterEmail(email);
        enterMobile(mobile);
        selectOccupation(occupationName);
        selectGender(gender);
        enterPassword(password);
        markAdult();
        submitRegistration();
    }
}
