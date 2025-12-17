# ⚽ Football Archive – Spring Boot REST API

A clean, secure Spring Boot REST API for managing **archived football match knowledge**.
This project demonstrates real-world backend engineering practices including REST design, security, persistence, validation, logging, and testing.

---

## 🧰 Tech Stack

* **Java:** 21
* **Framework:** Spring Boot
* **Security:** Spring Security (HTTP Basic, RBAC)
* **Persistence:** Spring Data JPA + H2 (in-memory)
* **Testing:** JUnit 5, Mockito, MockMvc
* **Build Tool:** Maven

---

## 📌 Project Overview

The application stores and exposes different types of football match knowledge:

* Match Reports
* Quotes
* Reference Links
* Match Collections

Key engineering concepts showcased:

* RESTful API design with proper HTTP semantics
* Role-based access control (USER / ADMIN)
* JPA inheritance for polymorphic domain modeling
* DTO-based API contracts
* Centralized exception handling
* Bean Validation
* File-based logging
* Unit, integration, and security testing

---

## ▶️ How to Run the Application

1. Clone the repository
2. Navigate to the project root
3. Start the application:

```bash
mvn spring-boot:run
```

The application starts at:

```
http://localhost:8080
```

To change the port:

```properties
server.port=8080
```

---

## 🧪 Running Tests

Run all tests using:

```bash
mvn test
```

Test coverage includes:

* Service layer unit tests
* Controller integration tests (MockMvc)
* Security tests (401 unauthorized & authenticated access)

---

## 🗄️ Database Configuration (H2)

* Embedded **H2 in-memory** database
* No external setup required
* Data resets on application restart

### H2 Console

Available at:

```
http://localhost:8080/h2-console
```

Connection details:

| Property | Value                  |
| -------- | ---------------------- |
| JDBC URL | jdbc:h2:mem:footballdb |
| Username | sa                     |
| Password | (empty)                |

---

## 📥 Loading Sample Data

Initial data can be added via the secured POST endpoint.

Example:

```bash
curl -u admin:admin123 \
  -H "Content-Type: application/json" \
  -X POST \
  -d '{"type":"REPORT","matchTitle":"El Clasico 2024","venue":"Santiago Bernabeu","reportText":"Barcelona defeated Real Madrid in El Clasico 2024 with a strong second-half performance."}' \
  http://localhost:8080/api/knowledge
```

---

## 🔌 Sample API Calls

### 1️⃣ Public Endpoint (No Authentication)

```bash
curl http://localhost:8080/api/knowledge/public
```

### 2️⃣ Secured GET Endpoint (USER / ADMIN)

```bash
curl -u user:user123 http://localhost:8080/api/knowledge
```

### 3️⃣ Secured POST Endpoint (ADMIN only)

```bash
curl -u admin:admin123 \
  -H "Content-Type: application/json" \
  -X POST \
  -d '{"type":"QUOTE","matchTitle":"UCL Final","venue":"Wembley","quoteText":"Football is life","speaker":"Manager"}' \
  http://localhost:8080/api/knowledge
```

---

## 👤 In-Memory Users

| Username | Password | Role  |
| -------- | -------- | ----- |
| user     | user123  | USER  |
| admin    | admin123 | ADMIN |

---

## 📝 Logging

* File-based logging enabled
* Log file location:

```
logs/app.log
```

Logged events include:

* Public access requests
* Authenticated access
* Archive creation events
* Conflict and validation failures

---

## 🧠 Engineering Notes

* JPA inheritance is used to model polymorphic match knowledge cleanly
* DTOs prevent entity leakage outside the service boundary
* Global exception handling ensures consistent API error responses
* Validation rules enforce domain correctness
* Spring Security is configured using HTTP method + endpoint matching

### Security Insight

During development, a generic URL matcher was evaluated before specific rules, unintentionally blocking access.
This was resolved by defining **method-specific authorization rules**, ensuring:

* Public users → public GET endpoint only
* USER role → read-only access
* ADMIN role → create and delete permissions

---

## 🧪 Testing Note (Mocked ID Simulation)

In service unit tests, Hibernate is not active.
To simulate database-generated IDs, repository responses are mocked:

```java
MatchReport saved = Mockito.mock(MatchReport.class);
Mockito.when(saved.getId()).thenReturn(1L);
Mockito.when(saved.getMatchTitle()).thenReturn("El Clasico 2024");
```

This mirrors real persistence behavior without starting the JPA layer.

---

## 🚀 What I Would Improve With 2 More Hours

* Add richer and more realistic sample data for all match types
* Extend the domain with squad lists, formations, and referee details
* Introduce response-level access control (basic vs detailed views)
* Expand validation edge-case test coverage
* Improve API documentation with Postman collections and response samples

---

## ✅ Current Status

* ✅ Application runs successfully
* ✅ Security behaves as expected
* ✅ Tests cover service, controller, and security layers


