package com.ikea.uitestcucumberikeadk.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import com.ikea.uitestcucumberikeadk.pages.LisaboTablePage;
import com.ikea.uitestcucumberikeadk.utils.DriverManager;

/**
 * Step definitions for the LISABO table product page feature.
 * The price assertion lives in {@link ProductPriceSteps}.
 */
public class LisaboTableSteps {

    private LisaboTablePage lisaboTablePage;

    private LisaboTablePage lisaboTablePage() {
        if (lisaboTablePage == null) {
            lisaboTablePage = new LisaboTablePage(DriverManager.getDriver());
        }
        return lisaboTablePage;
    }

    @Given("user opens the LISABO table product page in color {string}")
    public void userOpensTheLisaboTableProductPageInColor(String color) {
        lisaboTablePage().openInColor(color);
    }

    @When("user sets the quantity to {int}")
    public void userSetsTheQuantityTo(int quantity) {
        lisaboTablePage().setQuantity(quantity);
    }
}
