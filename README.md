# rest-gist-assignment-pq

This is my submission for the rest assured assignment against the GitHub gist API for your company.

## Requirements
- git
- Java 1.8

## Instructions for OsX

- Clone the repo\
`git clone https://github.com/sandeepthukral/rest-gist-assignment-pq.git`
- Once done, move into the repo folder\
`cd rest-gist-assignment-pq`
- Create a **new** GitHub user and create a token (https://github.com/settings/tokens/new) and provide it with the scope `gist`
- Copy the token and enter the following information in these files\
`src/test/resources/credentials.properties` token you just generated\
`src/test/resources/testData.properties` username of the GitHub user\
**Beware** - __All gists created on this account will be deleted during testing__
- Run the following command to execute the tests\
`./gradlew clean test`
- The report will be available in the folder `build/reports/allure-report`. \
Open it using the command
  `./gradlew allureServe`

## Instructions for Windows (this project has not been tested on Windows)
- Clone the repo \
`git clone https://github.com/sandeepthukral/rest-gist-assignment-pq.git`
- Once done, move into the repo folder\
`cmd rest-gist-assignment-pq`
- Create a **new** GitHub user and create a token (https://github.com/settings/tokens/new) and provide it with the scope `gist`
- Copy the token and enter the following information in these files\
`src\\test\\resources\\credentials.properties` token you just generated\
`src\\test\\resources\\testData.properties` username of the github user\
**Beware** - __All gists created on this account will be deleted during testing__
- Run the following command to execute the tests \
`gradlew.bat clean test`
- The report will be available in the folder `build\\reports\\allure-report`. Open it by navigating to and double-clicking the file \
`gradlew.bat allureServe`

## Components of the framework
- restAssured
- gradle
- Allure reporting 

## Structure 
`qa.sandeep.dto` - DTO for Gist and Gist Files
`qa.sandeep.gistClient` - The gists API client
`qa.sandeep.tests` - rest Assured tests
`qa.sandeep.utils` - Utility files 

## Classes
- *TestBase* - instantiates the GistClient and randomUtils, reads the testData props. These are then available to all tests
- *GistDTO and GistFileDTO* - POJOs to represent the gist creation payload
- *GistApiClient* - the Gist API client for interacting with the Gist API

## Notes on adding tests
- Create a new Test File in the package *qa.sandeep.tests*. 
- You may have to extend the GistApiClient class to support any part of the API that has not yet been created

## Thoughts on the work done

There are a few tests that are not covered for the lack of time. Some of these are\
- star and unstar gists
- getting a specific version of a gist
- fork a gist (needs a second user and more time)
- creating a longer file content and retrieving a truncated version 

The tests delete all gists of the test user. This is great for making the tests reliable and fast.\
But caution needs to be exercised to use a test GitHub account where the loss of data is not an issue.

### Resources used
I referred to the following resources when creating this solution
- https://developer.github.com/v3/gists/\
The API documentation 
- https://phauer.com/2016/testing-restful-services-java-best-practices/ \
A very relevant tutorial on best practices for testing RESTful services using Java / Kotlin