package com.ikea.uitestcucumberikeadk.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.ikea.uitestcucumberikeadk.utils.DriverManager;

/**
 * Scenario lifecycle: start a fresh browser before each scenario, capture a
 * screenshot if it failed, then quit. Keeps driver management out of the step
 * definitions.
 */
public class Hooks {

    @Before
    public void startBrowser() {
        DriverManager.startDriver();
    }

    // higher order runs first in the @After phase, so the screenshot is taken
    // while the browser is still open (before quitBrowser, order = 0)
    @After(order = 10)
    public void screenshotOnFailure(Scenario scenario) {
        if (!scenario.isFailed()) {
            return;
        }
        WebDriver driver = DriverManager.getDriver();
        if (driver instanceof TakesScreenshot takesScreenshot) {
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            // attaches to both the Cucumber report and the Allure report
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }

    @After(order = 0)
    public void quitBrowser() {
        DriverManager.quitDriver();
    }
}
