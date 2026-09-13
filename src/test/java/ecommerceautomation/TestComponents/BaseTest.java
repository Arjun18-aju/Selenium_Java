package ecommerceautomation.TestComponents;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import ecommerceautomation.pageobjects.LandingPage;
import ecommerceautomation.resources.ConfigReader;

public class BaseTest {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<LandingPage> LANDING_PAGE = new ThreadLocal<>();
    private static final ThreadLocal<ecommerceautomation.pageobjects.ProductCatalogue> PRODUCT_CATALOGUE = new ThreadLocal<>();
    private static final ThreadLocal<ecommerceautomation.pageobjects.ConfirmationPage> CONFIRMATION_PAGE = new ThreadLocal<>();
    private static final ThreadLocal<ecommerceautomation.pageobjects.OrderPage> ORDER_PAGE = new ThreadLocal<>();
    private static final ThreadLocal<ecommerceautomation.pageobjects.RegistrationPage> REGISTRATION_PAGE = new ThreadLocal<>();

    protected WebDriver driver;
    protected LandingPage landingPage;
    protected ecommerceautomation.pageobjects.ProductCatalogue productCatalogue;
    protected ecommerceautomation.pageobjects.ConfirmationPage confirmationPage;
    protected ecommerceautomation.pageobjects.OrderPage orderPage;
    protected ecommerceautomation.pageobjects.RegistrationPage registrationPage;

    protected WebDriver getDriver() {
        if (DRIVER.get() != null) {
            return DRIVER.get();
        }
        return driver;
    }

    protected void setDriver(WebDriver webDriver) {
        this.driver = webDriver;
        DRIVER.set(webDriver);
    }

    protected LandingPage getLandingPage() {
        if (LANDING_PAGE.get() != null) {
            return LANDING_PAGE.get();
        }
        return landingPage;
    }

    protected void setLandingPage(LandingPage landingPageInstance) {
        this.landingPage = landingPageInstance;
        LANDING_PAGE.set(landingPageInstance);
    }

    protected ecommerceautomation.pageobjects.ProductCatalogue getProductCatalogue() {
        if (PRODUCT_CATALOGUE.get() != null) {
            return PRODUCT_CATALOGUE.get();
        }
        return productCatalogue;
    }

    protected void setProductCatalogue(ecommerceautomation.pageobjects.ProductCatalogue productCatalogueInstance) {
        this.productCatalogue = productCatalogueInstance;
        PRODUCT_CATALOGUE.set(productCatalogueInstance);
    }

    protected ecommerceautomation.pageobjects.ConfirmationPage getConfirmationPage() {
        if (CONFIRMATION_PAGE.get() != null) {
            return CONFIRMATION_PAGE.get();
        }
        return confirmationPage;
    }

    protected void setConfirmationPage(ecommerceautomation.pageobjects.ConfirmationPage confirmationPageInstance) {
        this.confirmationPage = confirmationPageInstance;
        CONFIRMATION_PAGE.set(confirmationPageInstance);
    }

    protected ecommerceautomation.pageobjects.OrderPage getOrderPage() {
        if (ORDER_PAGE.get() != null) {
            return ORDER_PAGE.get();
        }
        return orderPage;
    }

    protected void setOrderPage(ecommerceautomation.pageobjects.OrderPage orderPageInstance) {
        this.orderPage = orderPageInstance;
        ORDER_PAGE.set(orderPageInstance);
    }

    protected ecommerceautomation.pageobjects.RegistrationPage getRegistrationPage() {
        if (REGISTRATION_PAGE.get() != null) {
            return REGISTRATION_PAGE.get();
        }
        return registrationPage;
    }

    protected void setRegistrationPage(ecommerceautomation.pageobjects.RegistrationPage registrationPageInstance) {
        this.registrationPage = registrationPageInstance;
        REGISTRATION_PAGE.set(registrationPageInstance);
    }

    public WebDriver initializeDriver() throws IOException {
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : ConfigReader.getBrowser();

        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("credentials_enable_service", false);
            chromePrefs.put("profile.password_manager_enabled", false);
            chromePrefs.put("profile.default_content_setting_values.notifications", 2);
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-features=PasswordManagerOnboarding,PasswordLeakDetection");
            options.addArguments("--incognito");
            options.addArguments("--window-size=1440,900");

            if (browserName.contains("headless")) {
                options.addArguments("--headless=new");
                options.addArguments("--remote-debugging-port=9222");
            }

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440, 900));
        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "/Users/rahulshetty//documents//geckodriver");
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "edge.exe");
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        setDriver(driver);
        return getDriver();
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return file.getAbsolutePath();
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        setDriver(initializeDriver());
        setLandingPage(new LandingPage(getDriver()));
        getLandingPage().goTo();
        return getLandingPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        driver = null;
        landingPage = null;
        productCatalogue = null;
        confirmationPage = null;
        orderPage = null;
        registrationPage = null;
        DRIVER.remove();
        LANDING_PAGE.remove();
        PRODUCT_CATALOGUE.remove();
        CONFIRMATION_PAGE.remove();
        ORDER_PAGE.remove();
        REGISTRATION_PAGE.remove();
    }
}
