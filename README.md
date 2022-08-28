# QA-Assignement

## Tools

Automation tool/framework used: **RestAssured** <br>
Programming Language: **Java _(Assignment Choice of Programming language)_**,</br>
Build Tool: **Maven**,</br>
Assertions/Annotation: **Junit 5**,</br>
CI Implementation: **Github Actions** </br>
IDE (recommended): **InteliJ**, <br>
Libraries used: </br>**- owner (For Configuration Management), </br>- Lombok (Cleaner Code) <br>- Allure-Report(For Extend reporting)** </br>

## Required software & Installation guide

* Java JDK 8+ installed and in your classpath
* Maven installed and in your classpath


## How to execute the tests in command line

- Clone the project
- Open the cmd and change directory(cd) to the cloned project folder
- Execute command to run all API Test: `mvn test`
- Execute command to run Smoke Tests: `mvn test -Dgroups="Smoke"`
- Execute command to run Smoke Tests with extended reports: `mvn test -Dgroups="Smoke" allure:report`
- Execute command to run all Tests with extended reports : `mvn clean test allure:report`

- Wait for tests to be completed and results will be in the console (please refer below to get a comprehensive report)

## How to execute test with comprehensive reports

- **Execute command to generate Html report** : `allure:report`
- **Execute command to Open Allure report in browser** : `mvn allure:serve`

- If not you can find the HTML report at `target/site/allure-maven-plugin` folder

## Things to highlight

    1. Builder, Base Test, Request and Responce Specification patterns applied
    2. Json Schema has been used to validate the contract
    3. Dynamic Test data, manage through Java Faker to ease the maintainability.
    4. Increase maintainability and increase readability
    5. Extended reporting provided through Allure Reporting
    6. Test case Tagging
    7. CI Implementation

## About the project structure

| Package Name        | File                     | Description                                                |
|:--------------------|:-------------------------|:-----------------------------------------------------------|
| `src\main\..config` | `RelatibeURI.java`       | Project relative/resource URLs are stored in there         |
|                     | `Configuration.java`     | Using owner all the configurations are getting mapped here |
| `src\main\..models` |                          | Implemented all the request DTO classes                    |
| `src\test\..data`   | `CreateBookingData.java` | Genarate all the TEst data using Faker library             |
| `src\test\..e2e`    |                          | All the test classes are included here                     |
| `src\test\..utils`  | `CommonUtil.java`        | Contains all the common methods                            |
| `test\resource`     | `schemas`                | This contain all the respond schemas                       |


#### Github Workflow Execution



#### Allure Report

