package com.ikea.uitestcucumberikeadk.utils;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Thin wrapper around {@link WebDriverWait} so page objects don't have to
 * build waits and {@link ExpectedConditions} by hand everywhere.
 */
public class WaitHelper {

    private final WebDriver driver;

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriverWait wait(Duration timeout) {
        return new WebDriverWait(driver, timeout);
    }

    public <T> T until(Function<WebDriver, T> condition) {
        return wait(Config.DEFAULT_TIMEOUT).until(condition);
    }

    public WebElement untilPresent(By locator, Duration timeout) {
        return wait(timeout).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement untilClickable(By locator, Duration timeout) {
        return wait(timeout).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement untilVisible(By locator, Duration timeout) {
        return wait(timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void untilInvisible(WebElement element, Duration timeout) {
        wait(timeout).until(ExpectedConditions.invisibilityOf(element));
    }

    public void untilUrlContains(String fragment) {
        wait(Config.DEFAULT_TIMEOUT).until(ExpectedConditions.urlContains(fragment));
    }
}
