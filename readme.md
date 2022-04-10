# Current Temperature Test Automation project

This Selenium/Cucumber project was created based on a job interview task.  

## Description

The task was to automate end-to-end testcase on [e-commerce website](https://weathershopper.pythonanywhere.com).  

`Selenium` & `Cucumber` test environment was created inside `Maven` project.

The BDD testcase was defined in `.feature` file. I tried to confirm with the requirement of single E2E testcase. Test data was defined in `.properties` file.  

The testcase logic was implemented in `StepDefinitions.java` file as `Gherkin` syntax is not complex enough.  

POM objects were created in `pageObject` folder that handle element recognition, page interactions and other operations specific to each webpage.  

`Selenium RemoteWebDriver` is used to run the test inside Docker container (see installation). The only requirement that wasn't met was the support of different browsers - test is currently running only in `Chrome` browser. However, I can imagine the solution with the help of `docker-compose` and several Docker containers.    

## Installation

1. Install Docker and Maven on host machine.

2. Pull and run Selenium Docker container:  
```
docker pull selenium/standalone-chrome:4.1.3-20220405  
docker run -d -p 4444:4444 -p 7900:7900 --shm-size=2g selenium/standalone-chrome:4.1.3-20220405
```

Testrun can be inspected in `http://localhost:7900` (password=secret). See [docker-selenium](https://github.com/SeleniumHQ/docker-selenium) for more info.

3. Clone this repo  
4. `mvn clean test`