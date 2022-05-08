# Current Temperature Test Automation project

This Selenium/Serenity/Cucumber test-automation project was created as a demonstration of Serenity capabilitites to automate end-to-end website tests.  

## Description

The task was to automate end-to-end testcase on [e-commerce website](https://weathershopper.pythonanywhere.com).  

`Serenity` environment was created implementing `Cucumber` BDD test structure.

Testcase business logic was defined in `.feature` file. I tried to confirm with the requirement of single E2E testcase.

Testcase was fully defined and implemented in `NewOrderDefinitions.java` step file as `Gherkin` syntax is not complex enough to handle technical details.  

Page objects were created in `pages` folder that handle element recognition, page interactions and other operations specific to each webpage.  

Soft assertion library `assertj` was used to ensure that all test steps are executed regardless of any failures. However, in this case it is not possible to bind failures to correct steps and all failures are shown within the step with `softAssert.assertAll()` call.

Tests were run via `Selenium Grid` server to optional browser and OS environments. Selenium Grid was started as `Docker` containers.  

## Installation

1. Install Docker, Docker Compose, Maven on the host machine.

2. Pull and run Selenium Grid Hub & Nodes containers:  

```
docker-compose up
```

Testrun can be inspected via ports `7900,7901,7902`. See `docker-compose.yml` and [docker-selenium](https://github.com/SeleniumHQ/docker-selenium) for more info.

3. Clone this repo  

4. Run tests

```
mvn clean verify -Dwebdriver.remote.driver=chrome
mvn clean verify -Dwebdriver.remote.driver=firefox
mvn clean verify -Dwebdriver.remote.driver=edge
```