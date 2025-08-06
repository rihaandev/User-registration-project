## User Registration App

A Spring Boot application for user registration, login, and user management with integrated Spring Security, Swagger (OpenAPI), and PostgreSQL. Dockerized for easy deployment.

### Features

- User registration and login
- Role-based access control using Spring Security
- Swagger UI for API documentation
- RESTful API design
- PostgreSQL as the backend database
- Docker + Docker Compose for containerized deployment
- Input validation with @Valid
- JPA and Hibernate integration

### Tech Stack

- Java 11
- Spring Boot
- Spring Data JPA
- Spring Security
- Hibernate Validator
- PostgreSQL
- Docker, Docker Compose
- Swagger/OpenAPI (Springdoc)

### Run with Docker

Make sure Docker and Docker Compose are installed.

Build and Start:

* `mvn clean package`
* `docker-compose up --build`

This will:

* Start a PostgreSQL container on port `5433`
* Build and run the Spring Boot app on port `3030`

### Base URL

All APIs are served under the base URL:

`http://localhost:3030/app`

### Swagger UI: `http://localhost:3030/app/swagger-ui.html`


### API Endpoints

| Endpoint                  | Method | Auth Required | Description          |
| ------------------------- | ------ | ------------- | -------------------- |
| /api/user/register      | POST   |  No          | Register a new user     |
| /api/user/login`        | POST   |  No          | Login a user            |
| /api/user/getAllUsers   | GET    | Admin only  | List all users           |
| /api/user?email=test@test | DELETE | Admin only  | Delete user by email   |


### Default Security

* Secured endpoints require **Basic Auth**
* Only `GET /getAllUsers` and `DELETE /user` require `ADMIN` role

Setup Admin User (Manual SQL)

After the app and database are running, you may need to manually insert an admin use to access protected endpoints.

Connect to the PostgreSQL container:

`docker exec -it user-db psql -U postgres -d user_registration`

Run SQL:

`
--05082025
--Script for inserting admin user to users table manually
INSERT INTO users (name, email, password, role)
VALUES ('Admin User', 'admin@example.com', 'admin123', 'ROLE_ADMIN');
`
### Object Mapping
Used **MapStruct** for converting between entity and value objects (VOs) to keep the code clean and maintainable.

### Logging
Implemented logging using **SLF4J** to trace application flow and handle error reporting effectively.

### Project Structure

* com.registration.app – Main Spring Boot app
* com.registration.domain – Entities, Value, Mapper & Enums
* com.registration.repository – JPA repositories
* com.registration.service – Business logic
* com.registration.rest – REST controllers
* com.registration.config – Config files SecurityConfig, SwaggerConfig
* src/test/java – JUnit test cases
* resources – scripts files

### Testing

Use Postman or Swagger UI to test API endpoints.

Example Swagger URL:

`http://localhost:3030/app/swagger-ui.html`

The project includes JUnit test cases using @SpringBootTest and Mockito for service-level unit testing.

Run Tests

`mvn clean test`

Test classes are located under:

`src/test/java/com/registration/service/`

Key test scenarios include:

* Register user successfully
* Duplicate email check
* Verify user