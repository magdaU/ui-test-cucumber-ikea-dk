package com.ikea.uitestcucumberikeadk.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ikea.uitestcucumberikeadk.utils.Config;

/**
 * Shared base for product pages. The price is rendered with the same markup
 * across products, so reading it lives here and is reused by every product
 * page object (and by the shared "the price is" step).
 */
public class ProductPage extends BasePage {

    protected static final By PRICE_INTEGER = By.cssSelector(".pipcom-price__integer");
    protected static final By PRICE_DECIMAL = By.cssSelector(".pipcom-price__decimal");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    protected void waitUntilPriceVisible() {
        wait.untilPresent(PRICE_INTEGER, Config.PRODUCT_PAGE_TIMEOUT);
    }

    public String getPrice() {
        String integerPart = driver.findElement(PRICE_INTEGER).getText();
        String decimalPart = driver.findElement(PRICE_DECIMAL).getText();
        return integerPart + decimalPart;
    }
}
