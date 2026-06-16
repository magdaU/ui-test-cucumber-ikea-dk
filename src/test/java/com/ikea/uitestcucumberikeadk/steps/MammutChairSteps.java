package com.ikea.uitestcucumberikeadk.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import com.ikea.uitestcucumberikeadk.pages.MammutChairPage;
import com.ikea.uitestcucumberikeadk.utils.DriverManager;

/**
 * Step definitions for the MAMMUT chair product page feature.
 * The price assertion lives in {@link ProductPriceSteps}.
 */
public class MammutChairSteps {

    private MammutChairPage mammutChairPage;

    private MammutChairPage mammutChairPage() {
        if (mammutChairPage == null) {
            mammutChairPage = new MammutChairPage(DriverManager.getDriver());
        }
        return mammutChairPage;
    }

    @Given("user opens the MAMMUT chair product page in color {string}")
    public void userOpensTheMammutChairProductPageInColor(String color) {
        mammutChairPage().openInColor(color);
    }

    @When("user sets the delivery postcode to {string}")
    public void userSetsTheDeliveryPostcodeTo(String postcode) {
        mammutChairPage().setDeliveryPostcode(postcode);
    }
}
