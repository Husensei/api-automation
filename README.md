# API Automation Test 

This project provides a set of API automation tests written in Java using RestAssured. These tests target the [ReqRes API](https://reqres.in/) and demonstrate various functionalities of RestAssured for testing RESTful APIs.
This is a basic example of API automation using RestAssured. You can extend this project to include more comprehensive test coverage for your specific API.

## Getting Started

This project uses Gradle for build automation. The Gradle Wrapper included in the project automatically downloads and runs the appropriate Gradle version required by the project. 
While you don't need to install Gradle to run the tests, you will need Java installed on your machine to execute the Java code within the tests. 
Refer to the official [Java download page](https://www.java.com/en/download/) for installation instructions.

## Dependencies
This project utilizes the following libraries:

* RestAssured: A popular library for simplifying HTTP request/response interactions in Java.
* TestNG: A testing framework for running and managing test cases.
* json-schema-validator (part of RestAssured): Provides functionality for validating JSON responses against JSON Schemas.

## Running the Tests
To run the tests, navigate to the project directory and run the following command `./gradlew test`.

Consider using a reporting framework like Allure Report to generate detailed test reports. Refer to the official [Allure Report Docs](https://allurereport.org/docs/install/) for installation instructions.

## Test Coverage

The `APITest` class includes several test methods that demonstrate different functionalities of RestAssured:

* `getUserTest`: This test retrieves a single user by ID and verifies the response status code and expected data.
* `validateJsonSchemaGetSingleUserTest`: This test retrieves a single user by ID and validates the response against a predefined JSON Schema.
* `createNewUserTest`: This test creates a new user with specific details and verifies the response status code and presence of generated user information.
* `updateUserPutTest`: This test updates an existing user using a PUT request and verifies the updated data.
* `updateUserPatchTest`: This test updates an existing user using a PATCH request and verifies the updated data.
* `deleteUserTest`: This test deletes a user by ID and verifies the response status code.
