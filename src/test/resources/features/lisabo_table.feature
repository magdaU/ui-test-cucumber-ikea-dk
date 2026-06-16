Feature: LISABO table product page

  Scenario Outline: The price of the LISABO table is shown correctly
    Given user opens the LISABO table product page in color "<color>"
    When user sets the quantity to <quantity>
    Then the price is "1.100.-"

    Examples:
      | color              | quantity |
      | Asketræsfiner      | 1        |
      | Sort/asketræsfiner | 2        |