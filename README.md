## Pay my Buddy

### Description
This project is a web application that allows users to manage their money transfers to other users. It is a Spring Boot application.

### Prerequisites
* Java 11
* Maven 3.6.3
* MySQL 8.0.23

### Installation
* Clone the repository
* Create a database named "paymybuddy" in MySQL or any other name
* Run the SQL script "paymybuddy.sql" in the database
* Possibly for tests, fixtures can be added via the fixtures.sql file
* Make a copy at the project root of the dist.env file renaming it .env and modify the required connection information
* Run the application with the command `mvn spring-boot:run`
* Create super user with url `http://localhost:8080/superuser?token=[TOKEN_SECRET]` with credentials in .env file