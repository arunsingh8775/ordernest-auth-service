# Auth Service

Spring Boot authentication service with JWT-based login and PostgreSQL persistence.

## Tech Stack
- Java 17
- Spring Boot 3.3.2
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (`jjwt`)

## Prerequisites
- JDK 17+
- PostgreSQL (or compatible managed PostgreSQL, such as Neon)
- Gradle wrapper (included)

## Configuration
Application settings are in `src/main/resources/application.yml`.

Current project values include:
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `spring.jpa.hibernate.ddl-auto=validate`
- `app.jwt.secret=${JWT_SECRET:change-me-in-prod-change-me-in-prod-change-me}`
- `app.seed.users-enabled=true`

`ddl-auto=validate` means required tables must already exist in the database.

## Run Locally
```bash
./gradlew bootRun
```

On Windows PowerShell:
```powershell
.\gradlew.bat bootRun
```

Default server URL: `http://localhost:8080`

## API
Base path: `/api/auth`

### 1) Register User
- Method: `POST`
- URL: `/api/auth/register`

Request body:
```json
{
  "email": "arun@example.com",
  "password": "Arun@1234",
  "firstName": "arun",
  "lastName": ""
}
```

Success response:
- Status: `201 Created`
- Body:
```json
{
  "message": "User registered successfully"
}
```

Possible errors:
- `400 Bad Request` (validation)
- `409 Conflict` (email already exists)

### 2) Login
- Method: `POST`
- URL: `/api/auth/login`

Request body:
```json
{
  "email": "arun@example.com",
  "password": "Arun@1234"
}
```

Success response:
- Status: `200 OK`
- Body:
```json
{
  "token": "<jwt-token>"
}
```

Possible errors:
- `400 Bad Request` (validation)
- `401 Unauthorized` (invalid credentials or inactive user)

## Seeded Sample Users
When the application starts, it seeds these users if they do not already exist:

- `admin@ordernestrandom123.com` / `Admin@1234` (`ADMIN`)
- `arun@ordernestrandom123.com` / `User@1234` (`USER`)
- `priya@ordernestrandom123.com` / `User@1234` (`USER`)
- `rahul@ordernestrandom123.com` / `User@1234` (`USER`)
- `neha@ordernestrandom123.com` / `User@1234` (`USER`)

## Notes
- JWT expiry is currently 1 hour.
- JWT signing secret is read from `app.jwt.secret` (env: `JWT_SECRET`).
- Passwords are stored using BCrypt hashing.
