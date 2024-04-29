# User Management Web Application

Hello and welcome to the online book store. This project is a web application designed to provide users with a convenient and accessible way to purchase and receive the books they desire.

## Overview

You can watch a short video presentation of the project: [Store Overview](https://www.loom.com/share/28eb2fac38354307b9b1f1358581d13c)

### Functionality

- **User**: Have access to authentication, browsing the catalog of books, viewing details of each book, exploring book categories, sorting book lists, adding books to the cart, and placing orders with specified delivery addresses.

- **Admin**: Have access to adding new books and categories, updating books and categories, managing order status, and handling other administrative tasks. Our admins have full control over the store's inventory and features.

### Controllers

- **UserController**: Provides endpoints for registering new users and authenticating existing users. This controller helps ensure security and user management in the application, making it a key component for user authentication and registration in the app. It handles HTTP requests and provides documentation through Swagger, simplifying user interaction with the system.


### Technical Stack and Tools

- **Data Validation**: The application uses Hibernate Validator and Jakarta Validation annotations to validate user input data. Our system ensures that all user-entered data is valid and meets requirements.

- **Programming Language and Frameworks**: The project is written in Java and utilizes the Spring Boot framework. This technology stack is chosen to ensure high performance and scalability of the web application.

- **Database Management**: MySQL is used as the database with the capability of extending to other SQL databases. Our database stores a vast amount of information about books and orders.

- **Libraries and Technologies**: The project utilizes libraries such as MapStruct and Lombok, along with the Liquibase technology for convenient and rapid data development and management.

## Usage

To get started with the "User Management Web Application" you need to follow a few simple steps.

1. **Clone the project**: Clone the repository and navigate to the project folder: `git clone https://github.com/IhorKalaur/Demo.git` and `cd bookstore`.
2. **Configure the database**: Set the necessary configuration for connecting to your database in the application.properties file.
3. **Install Maven**: Run the command for building and configuring the project: `mvn clean install`.
4. **Run the project**: Start the application using Maven: `mvn spring-boot:run`.
5. **URL**: After these steps, the API will be accessible at [URL](http://localhost:8080/api).
6. **Add initial data**: Add initial data and an administrator through the console or API. By default, there is already an administrator in the database with the username "admin@admin.ua" and password "12345678".
8. **Swagger**: You can check the API documentation using Swagger. It provides a simple and intuitive interface for verifying API functions: [Swagger](http://localhost:8080/api/swagger-ui/index.html).

## Community Contribution

I appreciate the assistance and contributions of users and community members. You can also contribute to the project, even if you are not a developer. This can include:

1. Reporting bugs and issues.
2. Participating in testing and reporting results.
3. Translating documentation or the interface.
4. Proposing new features and capabilities.

## Contact

If you have questions, suggestions, or need assistance, please feel free to reach out to us via email at: `ihorkalaur@gmail.com`.
