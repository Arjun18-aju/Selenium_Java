package ecommerceautomation.stepDefinitions;

import java.io.IOException;

import ecommerceautomation.TestComponents.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseTest {

    @Before(order = 0)
    public void setUp() throws IOException {
        if (getLandingPage() == null) {
            launchApplication();
        }
    }

    @After(order = 0)
    public void tearDownScenario() {
        tearDown();
    }
}
