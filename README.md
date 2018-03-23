# Fibonacci API

This is a Java application built using the Spring Boot framework. 
It contains a REST API which can return a Fibonacci Sequence of the length specified by the user.


## Prerequisites
A Java Runtime Environment (JRE) is required to run the application. 


## Building (optional)
It is not necessary to build the application as the executable JAR file has already been built and resides in the
build/libs directory (fibonacci-api-1.0.jar). 

The project uses the Gradle build tool. A Java Development Kit (JDK) is required to build the application. 
The JAVA_HOME environment variable is used by the Gradle build script to compile.

To build the application, run the following command from the root directory:

    gradlew build

An executable JAR file will be created and stored in the build/libs directory.


## Starting the Application

The executable jar can be run using the following command:

    java -jar fibonacci-api-1.0.jar

As it is a Spring Boot application, the jar contains an embedded Tomcat servlet container. The application runs on port 8080.


## Running the tests
The JAVA_HOME environment variable must be set to run the tests. Use the following command:
    
    gradlew test


## Example Fibonacci Request
You can use any client that can send a HTTP GET request i.e. browser, curl, Postman etc.

The URI to get the Fibonacci sequence is as follows:

    http://localhost:8080/fibonacci

The size of the list is specified by a query parameter called 'length'. It is required and cannot be a negative number.

#### Sample Request using Curl:

    curl http://localhost:8080/fibonacci?length=5

##### Expected Response

    [0,1,1,2,3]

### Secured Endpoint
The 'fibonacci' endpoint is public. I have also added an endpoint 'fibonacci-secure', that is secured using Spring Security and 
JWT. This endpoint can only be accessed if a valid JWT is found in the header of the request.

In order to test the secure endpoint you should first try to access it without a token:

    curl http://localhost:8080/fibonacci-secure?length=5

You should get a 403 response:

    "status":403,"error":"Forbidden","message":"Access Denied","path":"/fibonacci-secure"

Then log in to the application by sending the following POST request:

    curl -i -H "Content-Type: application/json" -X POST -d '{
        "username": "admin",
        "password": "password"
    }' http://localhost:8080/login

If the request is successful, you should see a JWT in the Authorization header of the response:

For example:

    Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyMTgyNDc5OX0.11xW7gvlCRmZ1O-rgJHQjbz_FPSvc2V5E8HklL0PV4itapY31OnZMEjEaz5yrzoU9UUBEJmkrNQo3pw2vqMNNg

You should then be able to include this token in the Authorization header to send a successful request to the secure endpoint:

    curl -i -H "Content-Type: application/json" \
    -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyMTgyNDc5OX0.11xW7gvlCRmZ1O-rgJHQjbz_FPSvc2V5E8HklL0PV4itapY31OnZMEjEaz5yrzoU9UUBEJmkrNQo3pw2vqMNNg" \
    -X GET http://localhost:8080/fibonacci-secure?length=5
    
The token is valid for 24 hours. 

