package com.ikea.uitestcucumberikeadk.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import com.ikea.uitestcucumberikeadk.utils.DriverManager;

/**
 * Scenario lifecycle: start a fresh browser before each scenario and quit it
 * afterwards. Keeps driver management out of the step definitions.
 */
public class Hooks {

    @Before
    public void startBrowser() {
        DriverManager.startDriver();
    }

    @After
    public void quitBrowser() {
        DriverManager.quitDriver();
    }
}
