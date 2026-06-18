Feature: LISABO table availability information

  Scenario Outline: Availability information is shown on the LISABO table product page
    Given user opens the LISABO table product page in color "<color>"
    Then the availability information is displayed

    Examples:
      | color              |
      | Asketræsfiner      |
      | Sort/asketræsfiner |