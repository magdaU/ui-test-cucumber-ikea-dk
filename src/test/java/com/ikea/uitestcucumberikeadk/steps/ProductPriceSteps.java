package com.ikea.uitestcucumberikeadk.steps;

import io.cucumber.java.en.Then;

import com.ikea.uitestcucumberikeadk.pages.ProductPage;
import com.ikea.uitestcucumberikeadk.utils.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Shared price assertion. The price markup is the same across product pages,
 * so a single step serves LISABO, MAMMUT and any future product feature.
 */
public class ProductPriceSteps {

    @Then("the price is {string}")
    public void thePriceIs(String expectedPrice) {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        assertThat(productPage.getPrice()).isEqualTo(expectedPrice);
    }
}
