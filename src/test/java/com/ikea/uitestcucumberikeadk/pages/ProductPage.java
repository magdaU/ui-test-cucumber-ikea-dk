package com.ikea.uitestcucumberikeadk.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
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
        // the price can re-render (e.g. after a postcode/delivery change) and is
        // briefly empty, so wait until the integer part is actually rendered
        wait.until(d -> {
            try {
                return !d.findElement(PRICE_INTEGER).getText().isBlank();
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                return false;
            }
        }, Config.PRODUCT_PAGE_TIMEOUT);

        String integerPart = driver.findElement(PRICE_INTEGER).getText();
        String decimalPart = driver.findElement(PRICE_DECIMAL).getText();
        return integerPart + decimalPart;
    }

    /**
     * The delivery/availability section loads asynchronously after the rest of
     * the page. IKEA changes its CSS class names often, so instead of a brittle
     * selector we wait for the section's Danish copy (delivery / collection /
     * stock) to appear in the rendered page.
     */
    public boolean isAvailabilityInformationDisplayed() {
        try {
            wait.until(d -> {
                String source = d.getPageSource();
                return source.contains("Levering")
                        || source.contains("Afhentning")
                        || source.toLowerCase().contains("lager");
            }, Config.PRODUCT_PAGE_TIMEOUT);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
