package com.ikea.uitestcucumberikeadk.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page object for the LISABO table product page (color, quantity, price).
 */
public class LisaboTablePage extends ProductPage {

    private static final String ASKETRAESFINER_URL =
            "https://www.ikea.com/dk/da/p/lisabo-bord-asketraesfiner-40416498/";
    private static final String SORT_ASKETRAESFINER_URL =
            "https://www.ikea.com/dk/da/p/lisabo-bord-sort-asketraesfiner-50416501/";

    private static final By QUANTITY_INPUT = By.cssSelector(".pipf-quantity-stepper__input");
    private static final By QUANTITY_INCREASE = By.cssSelector(".pipf-quantity-stepper__increase");

    public LisaboTablePage(WebDriver driver) {
        super(driver);
    }

    public void openInColor(String color) {
        String url = switch (color) {
            case "Asketræsfiner" -> ASKETRAESFINER_URL;
            case "Sort/asketræsfiner" -> SORT_ASKETRAESFINER_URL;
            default -> throw new IllegalArgumentException("Unknown LISABO color: " + color);
        };
        driver.get(url);
        waitUntilPriceVisible();

        dismissCookieBannerIfPresent();
    }

    public void setQuantity(int quantity) {
        WebElement quantityInput = driver.findElement(QUANTITY_INPUT);
        WebElement increaseButton = driver.findElement(QUANTITY_INCREASE);
        while (Integer.parseInt(quantityInput.getAttribute("value")) < quantity) {
            increaseButton.click();
        }
    }
}
