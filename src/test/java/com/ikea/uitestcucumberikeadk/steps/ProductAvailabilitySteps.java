package com.ikea.uitestcucumberikeadk.steps;

import io.cucumber.java.en.Then;

import com.ikea.uitestcucumberikeadk.pages.ProductPage;
import com.ikea.uitestcucumberikeadk.utils.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Shared availability assertion. The delivery/availability markup is the same
 * across product pages, so a single step serves LISABO, MAMMUT and any future
 * product feature.
 */
public class ProductAvailabilitySteps {

    @Then("the availability information is displayed")
    public void theAvailabilityInformationIsDisplayed() {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        assertThat(productPage.isAvailabilityInformationDisplayed()).isTrue();
    }
}