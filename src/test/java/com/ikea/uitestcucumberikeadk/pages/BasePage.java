package com.ikea.uitestcucumberikeadk.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import com.ikea.uitestcucumberikeadk.utils.Config;
import com.ikea.uitestcucumberikeadk.utils.WaitHelper;

/**
 * Common base for all page objects: holds the driver, a {@link WaitHelper}
 * and the shared cookie-consent handling.
 */
public abstract class BasePage {

    private static final By COOKIE_ACCEPT = By.id("onetrust-accept-btn-handler");

    protected final WebDriver driver;
    protected final WaitHelper wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    public boolean isCookieBannerVisible() {
        return !driver.findElements(COOKIE_ACCEPT).isEmpty();
    }

    protected void dismissCookieBannerIfPresent() {
        try {
            wait.untilClickable(COOKIE_ACCEPT, Config.COOKIE_BANNER_TIMEOUT).click();
        } catch (TimeoutException e) {
            // cookie consent banner did not appear, nothing to dismiss
        }
    }
}
