# User Management Web Application

HHello, I invite you to familiarize yourself with the service that helps to administer information about users. This project is a web application designed to provide a convenient and accessible way to store and update user information. Feel free to use this service

## Overview

### UserController

Provides endpoints for maneging  users. It handles HTTP requests and provides documentation through Swagger, simplifying user interaction with the system.
- Create User Endpoint: Allows creating a new user. It verifies that the user has reached the age of majority before creation.
- **Update Any User Fields Endpoint: Enables updating specific fields of an existing user without creating a new user.
-  Update All User Fields Endpoint: Updates all information about a user, including creating a new record if the user does not exist.
-  Find Users by Birth Date Range Endpoint: Retrieves a list of users whose birth dates fall within a specified range provided in the YYYY-MM-DD format.
-  Delete User Endpoint: Marks a user as deleted, implementing the soft delete mechanism to retain information.


### Technical Stack and Tools

- **Data Validation**: The application uses Hibernate Validator and Jakarta Validation annotations to validate user input data. Our system ensures that all user-entered data is valid and meets requirements.

- **Programming Language and Frameworks**: The project is written in Java and utilizes the Spring Boot framework. This technology stack is chosen to ensure high performance and scalability of the web application.

- **Database Management**: MySQL is used as the database with the capability of extending to other SQL databases. Our database stores a vast amount of information about books and orders.

- **Libraries and Technologies**: The project utilizes libraries such as MapStruct and Lombok, along with the Liquibase technology for convenient and rapid data development and management.

## Usage

To get started with the "User Management Web Application" you need to follow a few simple steps.

1. **Clone the project**: Clone the repository and navigate to the project folder: `git clone https://github.com/IhorKalaur/Demo.git` and `cd bookstore`.
2. **Configure the database**: Set the necessary configuration for connecting to your database in the application.properties file. You must set your own "datasource.url", "datasource.username" and "datasource.password"
3. **Install Maven**: Run the command for building and configuring the project: `mvn clean install`.
4. **Run the project**: Start the application using Maven: `mvn spring-boot:run`.
5. **URL**: After these steps, the API will be accessible at [URL](http://localhost:8080/api).
8. **Swagger**: You can check the API documentation using Swagger. It provides a simple and intuitive interface for verifying API functions: [Swagger](http://localhost:8080/api/swagger-ui/index.html).

## Community Contribution

I appreciate the assistance and contributions of users and community members. You can also contribute to the project, even if you are not a developer. This can include:

1. Reporting bugs and issues.
2. Participating in testing and reporting results.
3. Translating documentation or the interface.
4. Proposing new features and capabilities.

## Contact

If you have questions, suggestions, or need assistance, please feel free to reach out to us via email at: `ihorkalaur@gmail.com`.
