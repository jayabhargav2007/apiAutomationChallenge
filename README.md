
# Automation of https://jsonplaceholder.typicode.com/ rest API endpoints

This Project is a small POC of Serenity BDD framework using Cucumber, Rest-Assured and Java.
It uses Selenium 3 webdriver for the web automation and Rest-Assured for API-Automation.
Maven has been used for dependency management.

**Table of Contents**
* [Task](#task)
* [Functionality](#flow)
* [Behavior Driven Development](#behavior-driven-development)
* [Testing Framework](#testing-framework)
* [Testing Coverage](#testing-coverage)
* [How to Run the tests](#how-to-run-the-tests)
    * [Using Maven](#1-using-maven)
    * [Using IDE](#2-using-ide)
    * [Test Result](#3-test-result)


# Task
* Task here is 
1. To create a test automation framework skeleton.
2. To perform the validations for the comments for the post made by a
specific user.
   
# Flow
  1. Search for the user with username “Delphine”.
  2. Use the details fetched to make a search for the posts written by the
  user.
  3. For each post, fetch the comments and validate if the emails in the
  comment section are in the proper format.

* All the functional tests are created in feature files (using Gherkin).
* Step definition are written in Java class.
* API methods are written in Java class.

# Behavior Driven Development

The test cases here in the automation framework are added in BDD format.
The reason for choosing to writ tests  in BDD format are:

		1) Currently many teams are using BDD in SDLC as Acceptance criteria are very well available for both Development and testing
		2) Using BDD Acceptance test Will help the business understand the test cases very easily

# Testing Framework

I have used BDD Test Automation framework Using SerenityBDD and Rest Assured. Serenity BDD is a library which uses Cucumber and rest-assured for its API test development. There are several alternatives(like karate DSL, Chakram, postman scripts .. etc ) options out in the market but below are reason to use Serenity

### Reasons for choosing Serenity framework:

		1) Easily maintainable automated acceptance criteria
		2) Compatiable with several opensource tools also built on famous libraries like selenium, cucumber and rest-assured
		3) Storage of data in runtime for easy access
		4) Living documentation of test results
		5) Opensource tool with huge support Online

# Testing Coverage

Test coverage is a living documentation thats generated from test automation, this a huge advantage of using Serenity BDD.

Detailed test coverage report along with the results and clasification can be found [here_on_this_link](https://circleci.com/api/v1.1/project/github/sripriyavasan/api-automation-challenge/latest/artifacts/0/target/site/serenity/index.html)

![Alt text](TestResults.png?raw=true "Test Results and Test coverage ScreenShot")
# How to Run the tests

	# Pre-requisite:
	* JDK 8
	* Maven is installed in the machine and configured properly


## 1) Using Maven

	Open a command window and run:

		WindowsOS:	mvn clean verify

		MacOs : mvn clean verify 

	To Run Specific Tags of a test (Specific group of tests):

	  	mvn clean verify -Dcucumber.options="--tags @TAGNAME"

## 2) Using IDE
	Open the cloned project in IDE. Enable Auto-Import for Maven-dependency
	Run Testrunner files in the path (\src\test\java\runner\RunAllTest.java)


## 3) Test Result
Serenity BDD has one of the best reporting and the test reports are generated every time we execute the tests.
When the project is cloned into Local, test results reporting, along with screenshots, can be seen if we open **_`index.html`_** from `(target\site\serenity\index.html)`.

Also last executed results can be found in this [link](https://circleci.com/api/v1.1/project/github/sripriyavasan/api-automation-challenge/latest/artifacts/0/target/site/serenity/index.html)


Kindly let me know if you need any further Information. You can contact me on my emailID.
