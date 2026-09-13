package ecommerceautomation.TestComponents;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Allure;

public class Listeners extends BaseTest implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Allure.step("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getThrowable() != null) {
            Allure.addAttachment("Failure details", "text/plain", result.getThrowable().toString(), "txt");
        }

        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String filePath = null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (filePath != null && Files.exists(Paths.get(filePath))) {
            try {
                Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(Files.readAllBytes(Paths.get(filePath))), "png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Allure.step("Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        Allure.step("Test failed but within success percentage");
    }

    @Override
    public void onStart(ITestContext context) {
        // no-op
    }

    @Override
    public void onFinish(ITestContext context) {
        // no-op
    }
}
