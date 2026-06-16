Feature: MAMMUT children's chair product page

  Scenario Outline: The price of the MAMMUT Børnestol is shown correctly
    Given user opens the MAMMUT chair product page in color "<color>"
    When user sets the delivery postcode to "<postcode>"
    Then the price is "139.-"

    Examples:
      | color                     | postcode |
      | indendørs/udendørs/lilla  | 8000     |
      | indendørs/udendørs/grøn   | 8200     |