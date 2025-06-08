# Microservice Project Overview

This project demonstrates a basic microservice architecture using Spring Boot (v3.5.0), Java 21, H2 in-memory databases, and REST APIs. It includes:

- **UserService**: Handles user creation, retrieval, update, and deletion.
- **OrderService**: Handles order creation, retrieval, update, and deletion. It calls UserService to validate the existence of a user before placing an order.

---

## 🔧 Technologies Used

- Java 21
- Spring Boot 3.5.0
- Spring Data JPA
- Spring Web
- Spring Cloud OpenFeign
- H2 Database (in-memory)
- JUnit 5
- Mockito
- Lombok

A Postman collection is provided in the root directory (postman_collection.json) to test endpoints of both services.

To generate code coverage reports:

Right-click on the test class or package (e.g. OrderServiceApplicationTests)

Click “Run with Coverage”

IntelliJ will show line-by-line coverage and percentage per class

API Endpoints
🔹 UserService (localhost:8080)
🔹 OrderService (localhost:8081)
