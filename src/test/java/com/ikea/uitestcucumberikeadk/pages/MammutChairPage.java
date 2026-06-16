package com.ikea.uitestcucumberikeadk.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ikea.uitestcucumberikeadk.utils.Config;

/**
 * Page object for the MAMMUT children's chair product page
 * (color, delivery postcode, price).
 */
public class MammutChairPage extends ProductPage {

    private static final String LILLA_URL =
            "https://www.ikea.com/dk/da/p/mammut-bornestol-indendors-udendors-lilla-90581990/";
    private static final String GROEN_URL =
            "https://www.ikea.com/dk/da/p/mammut-bornestol-indendors-udendors-gron-70581986/";

    private static final By POSTCODE_OPEN_BUTTON = By.cssSelector("button[aria-label='Indtast postnummer']");
    private static final By POSTCODE_INPUT = By.id("hnf-postalcode");
    private static final By POSTCODE_SAVE_BUTTON =
            By.xpath("//div[contains(@class,'hnf-postalcode-picker-modal')]//button[normalize-space()='Gem']");

    public MammutChairPage(WebDriver driver) {
        super(driver);
    }

    public void openInColor(String color) {
        String url = switch (color) {
            case "indendørs/udendørs/lilla" -> LILLA_URL;
            case "indendørs/udendørs/grøn" -> GROEN_URL;
            default -> throw new IllegalArgumentException("Unknown MAMMUT color: " + color);
        };
        driver.get(url);
        waitUntilPriceVisible();

        dismissCookieBannerIfPresent();
    }

    public void setDeliveryPostcode(String postcode) {
        driver.findElement(POSTCODE_OPEN_BUTTON).click();

        WebElement postcodeInput = wait.untilVisible(POSTCODE_INPUT, Config.DEFAULT_TIMEOUT);
        postcodeInput.clear();
        postcodeInput.sendKeys(postcode);

        // the save button is re-rendered while typing, so poll until it is
        // both present and enabled before clicking
        wait.until(d -> {
            try {
                WebElement saveButton = d.findElement(POSTCODE_SAVE_BUTTON);
                if (saveButton.isEnabled()) {
                    saveButton.click();
                    return true;
                }
                return false;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        wait.untilInvisible(postcodeInput, Config.DEFAULT_TIMEOUT);
    }
}
