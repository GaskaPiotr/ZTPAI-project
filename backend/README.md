# Spring Boot Boilerplate v1

A robust, production-ready Spring Boot boilerplate designed to jumpstart your next backend project. It comes pre-configured with industry best practices, including JWT authentication stored securely in cookies, database migrations, Docker integration, and comprehensive exception handling.

## Tech Stack & Features
* **Java 21** & **Spring Boot 4.0.3**
* **Security:** JWT + Cookie authorization
* **Database:** PostgreSQL (Dockerized) with Flyway for automated schema migrations
* **Architecture:** Controller -> Service -> Repository pattern with DTOs
* **Mapping:** MapStruct for efficient object mapping
* **Documentation:** OpenAPI / Swagger UI
* **Testing:** Pre-configured with JUnit, AssertJ, and Testcontainers

---

## Getting Started

To get this project running on your local machine, follow these steps exactly. You will need **Java 21**, **Maven**, and **Docker** installed.

### 1. Clone the repository
```bash
git clone https://github.com/GaskaPiotr/spring-boot-boilerplate.git
cd spring-boot-boilerplate
```

### 2. Create and Configure the `.env` File (CRITICAL)
This application relies on an environment file to manage secrets. **The app and the database container will not start without it.**

1. In the root directory of the project, create a new file named exactly `.env`.
2. Copy and paste the following block into your `.env` file.
3. Replace the placeholder values with your own secure credentials.

```env
# Database Configuration (Used by both Docker Compose and Spring Boot)
DB_PASSWORD=your_secure_postgres_password

# JWT Security
JWT_SECRET_KEY=your_super_secret_jwt_key_that_is_at_least_256_bits_long
# Time in milliseconds
JWT_EXPIRATION=86400000

# Default Admin Credentials
ADMIN_EMAIL=admin@example.com
ADMIN_PASSWORD=secure_admin_password
```

### 3. Spin up the Database
We use Docker Compose to run the PostgreSQL database locally. Docker will automatically read your `DB_PASSWORD` from the `.env` file you just created.

Run the following command in the root of the project:
```bash
docker compose up -d
```
*This starts a PostgreSQL 16 container on `localhost:5432` with the database name `my_database` and user `user`.*

### 4. Run the Application
Once the database container is running, you can start the Spring Boot application. Flyway will automatically connect to the database and run any pending migrations.

Run via Maven:
```bash
mvn spring-boot:run
```
*(Alternatively, you can run the main application class directly from IntelliJ IDEA).*

---

## API Documentation
Once the application is running, Swagger UI is automatically generated and available to explore and test your API endpoints.

* **Swagger UI:** `http://localhost:8080/v1/swagger.html`
* **OpenAPI Specs:** `http://localhost:8080/v1/api-docs`