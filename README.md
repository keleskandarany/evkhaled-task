# EV Task App

Author: Khaled Eleskandarany

## Overview

The EV Task App is a demo application developed as part of E&V's coding challenge. It provides a Restful API with several endpoints for various functionalities. The app is built using Java Spring Boot 3.1.0 and utilizes Maven for dependency management.

## Key Features

- Simple login functionality, use:
````
email: any email ending with @engelvoelkers.com
password: evtaskpassword
````
- Listing properties
- Retrieving property details
- Retrieving similar properties based on a predefined (item-based) similarity algorithm

## Postman Documentation
[Link to Postman Docs](https://documenter.getpostman.com/view/24544611/2s93sgVVWb).

## Installation and Usage
To run the EV Task App locally, follow these steps:

if you have Docker installed, you can build and run the Docker image using the following commands:
```
docker-compoe build
docker-compoe up
```
**Please Note | Updated** There seems to be an issue with the mariadb user permissions inside the docker container.

**So Alternatively**
1. Ensure that you have Java JDK 17 and Maven installed on your system.
2. Clone the repository to your local machine.
3. Run `mvn clean install`
4. Make sure that you have a running mysql connection and create a db with the name `evtask`
5. Set those values in your application.properties file:
    ```
    spring.datasource.username=
    spring.datasource.password=
    ```
6. Open a terminal and navigate to the project's root directory.
7. Run the following command to build and run the application: `mvn spring-boot:run`
8. The application should now be running on your localhost with port 8080

## Dependencies

The EV Task App has the following dependencies:

- Java JDK 17
- Maven (for running with `mvn spring-boot:run`)
- Docker (for running with Docker)

## Issues and Limitations

Currently, the `GET /properties/:id` endpoint returns the similar objects along with the property details. In a real-world scenario with a large similarity database, it may be necessary to separate this into a dedicated endpoint for performance reasons.

## Alternative Implementations

I went with a Component-based package grouping, instead of a Domain-based one. That's due to the limited number of domains in this code challenge and to add more visibility about the underlying components.
In a real-world example with - possibly and understandably - a higher number of Domain Objects, we can instead go with a Domain-based packages' grouping 

## Contact

If you have any questions or need support, feel free to reach out to Khaled Eleskandarany at khaled@reo.so. You can also connect with Khaled on LinkedIn: [LinkedIn Profile](https://www.linkedin.com/in/khaled-eleskandarany-1b063bb0/).