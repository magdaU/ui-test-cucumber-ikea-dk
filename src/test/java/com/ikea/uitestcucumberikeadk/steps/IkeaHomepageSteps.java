package com.ikea.uitestcucumberikeadk.steps;

import java.time.Duration;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;

public class IkeaHomepageSteps {

    private WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Given("user opens the IKEA Denmark website")
    public void userOpensIkeaDkWebsite() {
        driver.get("https://www.ikea.com/dk/da/");
    }

    @Then("the page title contains {string}")
    public void pageTitleContains(String expectedTitle) {
        assertThat(driver.getTitle()).contains(expectedTitle);
    }

    @And("the cookie consent banner is visible")
    public void cookieConsentBannerIsVisible() {
        boolean bannerVisible = !driver.findElements(
                By.id("onetrust-accept-btn-handler")
        ).isEmpty();
        assertThat(bannerVisible).isTrue();
    }

    @When("user types {string} in the search box")
    public void userTypesInSearchBox(String searchTerm) {
        dismissCookieBannerIfPresent();

        WebElement searchInput = driver.findElement(By.id("ikea-search-input"));
        searchInput.sendKeys(searchTerm);
        // wait for the autocomplete dropdown to open before submitting,
        // otherwise pressing enter is sometimes swallowed
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> "true".equals(searchInput.getAttribute("aria-expanded")));
        searchInput.sendKeys(Keys.ENTER);
    }

    @Then("search results contain {string}")
    public void searchResultsContain(String expectedText) {
        // wait for the search results to load
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("search"));
        assertThat(driver.getPageSource()).containsIgnoringCase(expectedText);
    }

    private static final String MAMMUT_LILLA_URL = "https://www.ikea.com/dk/da/p/mammut-bornestol-indendors-udendors-lilla-90581990/";
    private static final String MAMMUT_GROEN_URL = "https://www.ikea.com/dk/da/p/mammut-bornestol-indendors-udendors-gron-70581986/";

    @Given("user opens the MAMMUT chair product page in color {string}")
    public void userOpensTheMammutChairProductPageInColor(String color) {
        String url = switch (color) {
            case "indendørs/udendørs/lilla" -> MAMMUT_LILLA_URL;
            case "indendørs/udendørs/grøn" -> MAMMUT_GROEN_URL;
            default -> throw new IllegalArgumentException("Unknown MAMMUT color: " + color);
        };
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".pipcom-price__integer")));

        dismissCookieBannerIfPresent();
    }

    @When("user sets the delivery postcode to {string}")
    public void userSetsTheDeliveryPostcodeTo(String postcode) {
        driver.findElement(By.cssSelector("button[aria-label='Indtast postnummer']")).click();

        WebElement postcodeInput = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("hnf-postalcode")));
        postcodeInput.clear();
        postcodeInput.sendKeys(postcode);

        By saveButtonLocator = By.xpath("//div[contains(@class,'hnf-postalcode-picker-modal')]//button[normalize-space()='Gem']");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            try {
                WebElement saveButton = d.findElement(saveButtonLocator);
                if (saveButton.isEnabled()) {
                    saveButton.click();
                    return true;
                }
                return false;
            } catch (StaleElementReferenceException e) {
                return false;
            }
        });

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOf(postcodeInput));
    }

    private static final String LISABO_ASKETRAESFINER_URL = "https://www.ikea.com/dk/da/p/lisabo-bord-asketraesfiner-40416498/";
    private static final String LISABO_SORT_ASKETRAESFINER_URL = "https://www.ikea.com/dk/da/p/lisabo-bord-sort-asketraesfiner-50416501/";

    @Given("user opens the LISABO table product page in color {string}")
    public void userOpensTheLisaboTableProductPageInColor(String color) {
        String url = switch (color) {
            case "Asketræsfiner" -> LISABO_ASKETRAESFINER_URL;
            case "Sort/asketræsfiner" -> LISABO_SORT_ASKETRAESFINER_URL;
            default -> throw new IllegalArgumentException("Unknown LISABO color: " + color);
        };
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".pipcom-price__integer")));

        dismissCookieBannerIfPresent();
    }

    @When("user sets the quantity to {int}")
    public void userSetsTheQuantityTo(int quantity) {
        WebElement quantityInput = driver.findElement(By.cssSelector(".pipf-quantity-stepper__input"));
        WebElement increaseButton = driver.findElement(By.cssSelector(".pipf-quantity-stepper__increase"));
        while (Integer.parseInt(quantityInput.getAttribute("value")) < quantity) {
            increaseButton.click();
        }
    }

    @Then("the price is {string}")
    public void thePriceIs(String expectedPrice) {
        String integerPart = driver.findElement(By.cssSelector(".pipcom-price__integer")).getText();
        String decimalPart = driver.findElement(By.cssSelector(".pipcom-price__decimal")).getText();
        assertThat(integerPart + decimalPart).isEqualTo(expectedPrice);
    }

    private void dismissCookieBannerIfPresent() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")))
                    .click();
        } catch (TimeoutException e) {
            // cookie consent banner did not appear, nothing to dismiss
        }
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
