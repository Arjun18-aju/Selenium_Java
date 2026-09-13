package ecommerceautomation.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;

public class ExtentReporterNG {

    public static void attachScreenshot(String testCaseName, WebDriver driver) {
        if (driver == null) {
            return;
        }

        String filePath = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
        try {
            Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(Files.readAllBytes(Paths.get(filePath))), "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
