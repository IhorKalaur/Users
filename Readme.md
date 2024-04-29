# User Management Web Application

Welcome! We invite you to familiarize yourself with our service that facilitates the administration of user information. This project is a web application designed to provide a convenient and accessible way to store and update user data. Feel free to utilize this service.
## Overview

### UserController


The UserController in the provided Java Spring Boot application handles various CRUD (Create, Read, Update, Delete) operations related to user management. Below, I'll expand on each method within the controller, providing more details about their functionality, use, and design rationale. The UserController is annotated with @RestController, which marks the class as a controller where every method returns a domain object instead of a view. It's designed to handle HTTP requests related to user operations.

## Endpoints and Their Functionalities

1) Create a new user:
   - Endpoint: POST /users
   - Description: This endpoint is responsible for creating a new user. The user must meet certain criteria specified in the application, such as being of legal age. It accepts user data packaged as a CreateUserRequestDto object, validates it, and processes it through the UserService.
   - Validation: The request body is validated for constraints defined in CreateUserRequestDto.
2) Update specific fields of an existing user:
   - Endpoint: PATCH /users/{id}
   - Description: This endpoint allows the partial update of an existing user's data. It is not used for creating new users but rather modifying attributes of an already existing user based on the user ID provided in the path.
   - Dynamic Update: The endpoint dynamically updates fields provided in the request without requiring a full replacement of the user object.
3) Update all fields of an existing user:
   - Endpoint: PUT /users/{id}
   - Description: Unlike the PATCH method, this PUT endpoint expects a full user detail modification and will replace all fields of the user object. If the user does not exist, depending on the implementation, it might create a new user.
   - Idempotency: Typically, PUT operations are idempotent, meaning multiple identical requests should have the same effect as a single request.
4) Search for users by birth date range:
   - Endpoint: GET /users/search
   - Description: This endpoint retrieves all users whose birthdates fall within a specified range. The date range is provided as query parameters and processed to filter users.
   - Flexibility: Useful for generating reports or for UI components where users need to find records between specific dates.
5) Delete a user by ID:
   - Endpoint: DELETE /users/{id}
   - Description: Responsible for deleting a user by their unique identifier. This often implements a soft delete mechanism, marking the user as deleted in the database instead of removing the record entirely.
   - Data Integrity: Soft deletes help in maintaining data integrity and allow recovery of deleted records if needed.

### Technical Stack and Tools

- **Data Validation**: The application uses Hibernate Validator and Jakarta Validation annotations to validate user input data. Our system ensures that all user-entered data is valid and meets requirements.

- **Programming Language and Frameworks**: The project is written in Java and utilizes the Spring Boot framework. This technology stack is chosen to ensure high performance and scalability of the web application.

- **Database Management**: MySQL is used as the database with the capability of extending to other SQL databases. Our database stores information about users.

- **Libraries and Technologies**: The project uses libraries such as MapStruct and Lombok, along with Liquibase technology for convenient and rapid database schema management.

## Usage

To get started with the "User Management Web Application" you need to follow a few simple steps.

1. **Clone the project**: Clone the repository and navigate to the project folder: `git clone https://github.com/IhorKalaur/Users.git` and `cd users`.
2. **Configure the database**: Set the necessary configuration for connecting to your database in the application.properties file. You must set your own "datasource.url", "datasource.username" and "datasource.password"
3. **Install Maven**: Run the command for building and configuring the project: `mvn clean install`.
4. **Run the project**: Start the application using Maven: `mvn spring-boot:run`.
5. **URL**: After these steps, the API will be accessible at [URL](http://localhost:8080/api).
8. **Swagger**: You can check the API documentation using Swagger, which provides a simple and intuitive interface to verify API functions at [Swagger UI](http://localhost:8080/api/swagger-ui/index.html).

## Community Contribution

I appreciate the assistance and contributions of users and community members. You can also contribute to the project, even if you are not a developer. This can include:

1. Reporting bugs and issues.
2. Participating in testing and reporting results.
3. Translating documentation or the interface.
4. Proposing new features and capabilities.

## Contact

If you have questions, suggestions, or need assistance, please feel free to reach out to us via email at: `ihorkalaur@gmail.com`.
