# UI Test Cucumber IKEA

[![UI Tests](https://github.com/magdaU/ui-test-cucumber-ikea/actions/workflows/tests.yml/badge.svg)](https://github.com/magdaU/ui-test-cucumber-ikea/actions/workflows/tests.yml)

Automated UI test project for [ikea.com/dk/da](https://www.ikea.com/dk/da/), built with Spring Boot, Selenium and Cucumber (BDD).

## Tech stack

- **Java 21**
- **Spring Boot 4.1.0**
- **Selenium WebDriver 4.31.0** (Chrome, headless)
- **Cucumber 7** + JUnit Platform Suite (Cucumber-JVM)
- **AssertJ**

## Project structure

The test code is organised in four layers:

```
🔵 Feature layer (Cucumber)      - test description in Gherkin
src/test/resources/features/*.feature

🟡 Step Definitions              - translate Gherkin -> Java, hold assertions
src/test/java/...steps/Hooks.java                - browser lifecycle (@Before/@After)
src/test/java/...steps/HomepageSteps.java
src/test/java/...steps/LisaboTableSteps.java
src/test/java/...steps/MammutChairSteps.java
src/test/java/...steps/ProductPriceSteps.java    - shared "the price is" step

🟢 Page Objects (POM)            - Selenium logic per page
src/test/java/...pages/BasePage.java             - shared driver/wait + cookie banner
src/test/java/...pages/ProductPage.java          - shared product price reading
src/test/java/...pages/HomePage.java
src/test/java/...pages/LisaboTablePage.java
src/test/java/...pages/MammutChairPage.java

⚙️ Utils                         - driver, wait, config
src/test/java/...utils/DriverManager.java        - WebDriver lifecycle (ThreadLocal)
src/test/java/...utils/WaitHelper.java           - WebDriverWait wrappers
src/test/java/...utils/Config.java               - base URL and timeouts
```

Plus the supporting wiring:

```
src/main/java/...UiTestCucumberIkeaApplication.java   - Spring Boot application entry point
src/test/java/...RunCucumberTest.java                  - Cucumber runner (JUnit Platform Suite)
src/test/java/...CucumberSpringConfiguration.java      - Cucumber + Spring integration
```

## Test scenarios

The `ikea_homepage.feature` file contains:

1. **Open the IKEA Denmark homepage** – checks the page title and the visibility of the cookie consent banner.
2. **Search for a product** – enters a query ("BILLY") into the search box and verifies that the results contain the searched term.

The `lisabo_table.feature` file contains:

1. **LISABO table price check** – opens the LISABO table product page in a given color (`Asketræsfiner` and `Sort/asketræsfiner`), sets the quantity and verifies that the price is "1.100.-".

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

> **Note:** the tests run against the live ikea.com site and can be flaky
> (search autocomplete, price re-rendering after a postcode change, etc.).
> A failing run is often transient — re-run before treating it as a real defect.

## Reporting (Allure)

Cucumber results are collected by Allure into `target/allure-results` on every
test run (configured via the `AllureCucumber7Jvm` plugin in `RunCucumberTest`).
When a scenario fails, a screenshot of the browser is captured in `Hooks` and
attached to the failing scenario, so it shows up directly in the Allure report.

Generate and open the HTML report:

```bash
mvnw.cmd allure:serve      # build a temporary report and open it in the browser
mvnw.cmd allure:report     # write a static report to target/site/allure-maven-plugin
```

## CI/CD

GitHub Actions runs the suite on every push and pull request
(`.github/workflows/tests.yml`): JDK 21 (Temurin), headless Chrome (preinstalled
on the runner), `./mvnw test`. Flaky live-site scenarios are retried via
`-Dsurefire.rerunFailingTestsCount=2`. The Allure results and Surefire reports
are uploaded as build artifacts (`allure-results`, `surefire-reports`).

## Roadmap — "PRO STANDARD" checklist

What makes this an industry-level framework, and where we are:

| # | Criterion | Status |
|---|-----------|--------|
| 1 | POM (Page Object Model) | ✅ done |
| 2 | Cucumber (BDD) | ✅ done |
| 3 | DriverFactory | ✅ done |
| 4 | Explicit waits | ✅ done |
| 5 | Config system | 🟡 partial — constants in `utils/Config`, not externalised to properties/env |
| 6 | Reporting (Allure) | ✅ done |
| 7 | CI/CD | ✅ done |
| 8 | Screenshot on failure | ✅ done |

## Running the application

```bash
./mvnw spring-boot:run
```