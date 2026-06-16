package com.ikea.uitestcucumberikeadk.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.ikea.uitestcucumberikeadk.pages.HomePage;
import com.ikea.uitestcucumberikeadk.utils.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the IKEA homepage feature. Only translates Gherkin to
 * page-object calls and holds the assertions; no Selenium logic here.
 */
public class HomepageSteps {

    private HomePage homePage;

    private HomePage homePage() {
        if (homePage == null) {
            homePage = new HomePage(DriverManager.getDriver());
        }
        return homePage;
    }

    @Given("user opens the IKEA Denmark website")
    public void userOpensIkeaDkWebsite() {
        homePage().open();
    }

    @Then("the page title contains {string}")
    public void pageTitleContains(String expectedTitle) {
        assertThat(homePage().getTitle()).contains(expectedTitle);
    }

    @And("the cookie consent banner is visible")
    public void cookieConsentBannerIsVisible() {
        assertThat(homePage().isCookieBannerVisible()).isTrue();
    }

    @When("user types {string} in the search box")
    public void userTypesInSearchBox(String searchTerm) {
        homePage().search(searchTerm);
    }

    @Then("search results contain {string}")
    public void searchResultsContain(String expectedText) {
        assertThat(homePage().getResultsPageSource()).containsIgnoringCase(expectedText);
    }
}
