# Football Archive – Spring Boot REST API

## JDK Requirement

* Java **21**

## Project Overview
This project is a Spring Boot REST API that manages archived football match knowledge.
The system supports different types of match knowledge such as match reports, quotes, reference links, and collections.
It demonstrates REST API design, Spring Security, JPA inheritance, validation, error handling, logging, and testing.

## How to Run the Application

1. Clone the repository
2. Navigate to the project root
3. Run the application:
mvn spring-boot:run

The application will start on:
http://localhost:8080, we can change it by going to resources/application.properties and changing the port using server.port=8080.

## How to Run Tests
Run all unit and integration tests using:
mvn test

Tests include:
* Service layer unit tests
* Controller integration tests using MockMvc
* Security tests (401 unauthorized and authenticated access)

## Database (H2)

* The application uses **embedded H2 (in-memory)** database
* No external database setup is required
* Data is reset every time the application restarts

### H2 Console

Available at:
http://localhost:8080/h2-console

Connection details:

* JDBC URL: `jdbc:h2:mem:footballdb`
* Username: `sa`
* Password: empty

---

## Loading Initial Sample Data

Initial data can be loaded by calling the POST endpoint after starting the application.

Example using curl:
curl -u admin:admin123 \
  -H "Content-Type: application/json" \
  -X POST \
  -d '{"type":"REPORT","matchTitle":"El Clasico 2024","venue":"Santiago Bernabeu","reportText":"Barcelona defeated Real Madrid in El Clasico 2024 with a strong second-half performance."}' \
  http://localhost:8080/api/knowledge


## Sample API Calls

### 1. Public Endpoint (No Authentication)

curl http://localhost:8080/api/knowledge/public

### 2. Secured GET Endpoint (User/Admin)

curl -u user:user123 http://localhost:8080/api/knowledge

### 3. Secured POST Endpoint (Admin Only)
curl -u admin:admin123 \
  -H "Content-Type: application/json" \
  -X POST \
  -d '{"type":"QUOTE","matchTitle":"UCL Final","venue":"Wembley","quoteText":"Football is life","speaker":"Manager"}' \
  http://localhost:8080/api/knowledge


## In-Memory Users

| Username | Password | Role  |
| -------- | -------- | ----- |
| user     | user123  | USER  |
| admin    | admin123 | ADMIN |

## Logging

* File logging is enabled
* Logs are written to:

```
logs/app.log
```

Logging is added for:

* Public access
* Authenticated access
* Creation of archived matches
* Conflict scenarios

## Engineering Notes

* Used JPA inheritance to model different match knowledge types cleanly
* DTOs are used to avoid exposing entities directly
* Centralized exception handling ensures consistent error responses
* Validation is enforced using Bean Validation annotations
* Security is implemented using HTTP Basic with role-based access (user,admin)
* During development, I noticed that Spring Security evaluates request matchers in order, and a generic path-based rule was matching first and blocking more specific access rules.
  -To fix this, I updated the security configuration to define access rules using both HTTP method and endpoint, instead of only matching on URL patterns.
  -This change ensured clear separation of responsibilities:
  -Public users can only access the public GET endpoint
  -USER role has read-only access (GET endpoints only)
  -ADMIN role has elevated permissions like creating and deleting archive entries
  
## What I Would Improve with 2 More Hours
*Add more realistic sample data covering all match types (reports, quotes, reference links, and collections) so the API feels closer to a real archive.
*Extend the match domain with a few additional fields such as squad list, formations played, and referee details to make archived matches richer and more useful.
*Improve the security configuration so users can gradually see more detailed match information (for example, basic data for public users and richer data for authenticated users).
*Add more test cases around validation edge cases, especially for minimum/maximum field lengths and missing mandatory fields.
*Improve API documentation by adding a Postman collection or clearer examples showing how each endpoint should be called and what responses to expect.

## Status

✅ Application runs successfully
✅ Security works as expected
✅ Tests cover service, controller, and security behavior

