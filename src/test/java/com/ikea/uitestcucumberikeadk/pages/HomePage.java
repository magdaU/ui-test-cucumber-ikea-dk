package com.ikea.uitestcucumberikeadk.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ikea.uitestcucumberikeadk.utils.Config;

/**
 * Page object for the IKEA Denmark homepage and its search box.
 */
public class HomePage extends BasePage {

    private static final By SEARCH_INPUT = By.id("ikea-search-input");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(Config.BASE_URL);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void search(String term) {
        dismissCookieBannerIfPresent();

        WebElement searchInput = driver.findElement(SEARCH_INPUT);
        searchInput.clear();
        searchInput.sendKeys(term);
        // wait for the autocomplete dropdown to open before submitting,
        // otherwise pressing enter is sometimes swallowed
        wait.until(d -> "true".equals(searchInput.getAttribute("aria-expanded")));

        // Enter is occasionally swallowed by the autocomplete widget, so keep
        // re-submitting until the browser actually navigates to the results page
        wait.until(d -> {
            if (d.getCurrentUrl().contains("search")) {
                return true;
            }
            try {
                searchInput.sendKeys(Keys.ENTER);
            } catch (StaleElementReferenceException e) {
                // input detached because navigation already started
            }
            return d.getCurrentUrl().contains("search");
        }, Config.SEARCH_TIMEOUT);
    }

    public String getResultsPageSource() {
        wait.untilUrlContains("search", Config.SEARCH_TIMEOUT);
        return driver.getPageSource();
    }
}
