# UI Test Cucumber IKEA

Automated UI test project for [ikea.com/dk/da](https://www.ikea.com/dk/da/), built with Spring Boot, Selenium and Cucumber (BDD).

## Tech stack

- **Java 21**
- **Spring Boot 4.1.0**
- **Selenium WebDriver 4.31.0** (Chrome, headless)
- **Cucumber 7** + JUnit Platform Suite (Cucumber-JVM)
- **AssertJ**

## Project structure

```
src/main/java/...UiTestCucumberIkeaApplication.java   - Spring Boot application entry point
src/test/resources/features/ikea_homepage.feature     - homepage and search scenarios (Gherkin)
src/test/resources/features/mammut_chair.feature       - MAMMUT chair product page scenarios (Gherkin)
src/test/java/...steps/IkeaHomepageSteps.java          - Cucumber step definitions (Selenium)
src/test/java/...RunCucumberTest.java                  - Cucumber runner (JUnit Platform Suite)
src/test/java/...CucumberSpringConfiguration.java      - Cucumber + Spring integration
```

## Test scenarios

The `ikea_homepage.feature` file contains:

1. **Open the IKEA Denmark homepage** – checks the page title and the visibility of the cookie consent banner.
2. **Search for a product** – enters a query ("BILLY") into the search box and verifies that the results contain the searched term.

The `mammut_chair.feature` file contains:

1. **MAMMUT Børnestol price check** – opens the MAMMUT chair product page in a given color (`indendørs/udendørs/lilla` and `indendørs/udendørs/grøn`), sets the delivery postcode (8000 and 8200) and verifies that the price is "139.-".

## Requirements

- Java 21 (JDK)
- **Google Chrome** installed (Selenium launches it in headless mode)
- Internet connection (tests run against the live ikea.com site)

Maven is not required locally – the project includes the Maven Wrapper (`mvnw` / `mvnw.cmd`).

## Running the tests

### Windows

```bash
mvnw.cmd test
```

### Linux / macOS

```bash
./mvnw test
```

Tests run in headless Chrome. Test results (Surefire reports) are written to `target/surefire-reports`.

## Running the application

```bash
./mvnw spring-boot:run
```