# Spring Demo Application

A Spring Boot demo application built with Kotlin showcasing REST API endpoints with PostgreSQL database integration.

## Technology Stack

- **Language:** Kotlin 1.9.25 with Java 21
- **Framework:** Spring Boot 3.4.6
- **Database:** PostgreSQL 15+
- **Build Tool:** Gradle with Kotlin DSL
- **Testing:** Kotest, Testcontainers, MockK

## Prerequisites

- Java 21 or higher
- Docker (for database)
- HTTPie (optional, for testing)

## Quick Start

### 1. Start the Database

```shell
docker run -d \
  --name postgres-db \
  -e POSTGRES_PASSWORD=mypassword \
  -e POSTGRES_DB=mydb \
  -v postgres-data:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres:15
```

### 2. Run the Application

```shell
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## Available Endpoints

### Greetings
- **GET** `/greetings` - Get a random greeting message

```shell
curl http://localhost:8080/greetings
# or with HTTPie
http http://localhost:8080/greetings
```

### Dad Jokes
- **GET** `/dad-jokes` - Get a random dad joke

```shell
curl http://localhost:8080/dad-jokes
# or with HTTPie
http http://localhost:8080/dad-jokes
```

### Names
- **POST** `/names` - Create a new name entry

```shell
curl -X POST http://localhost:8080/names \
  -H "Content-Type: application/json" \
  -d '{"name": "John"}'
# or with HTTPie
http POST http://localhost:8080/names name="John"
```

## Development Commands

### Build and Test
```shell
./gradlew build          # Build project and run tests
./gradlew test           # Run tests only
./gradlew clean build    # Clean build
```

### Database Operations
```shell
./gradlew flywayMigrate  # Run database migrations
```

## Testing

The project uses Testcontainers for integration tests with a real PostgreSQL database. Tests can be found in the `src/test/kotlin` directory.

```shell
./gradlew test           # Run all tests
```

## Project Structure

```
src/
├── main/kotlin/me/soenke/spring_demo/
│   ├── SpringDemoApplication.kt
│   ├── config/
│   │   └── SecurityConfig.kt
│   ├── dadjoke/
│   │   └── DadJokeController.kt
│   ├── greeting/
│   │   └── GreetingController.kt
│   └── name/
│       ├── NameController.kt
│       └── NameService.kt
└── test/kotlin/
    └── ... (test files)
```

## Installing HTTPie (Optional)

For easier API testing:

```shell
# macOS
brew install httpie

# Ubuntu/Debian
sudo apt install httpie

# Windows (with Chocolatey)
choco install httpie
```
