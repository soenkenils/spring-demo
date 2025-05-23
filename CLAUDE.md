# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

This is a Spring Boot project using Kotlin and Gradle. Common development commands:

**Build and Test:**
```bash
./gradlew build          # Build project and run tests
./gradlew test           # Run tests only
./gradlew bootRun        # Run the application locally
./gradlew clean build    # Clean build
```

**Database:**
```bash
./gradlew flywayMigrate  # Run database migrations (if configured)
```

## Architecture

**Technology Stack:**
- **Language:** Kotlin 1.9.25 with Java 21 toolchain
- **Framework:** Spring Boot 3.4.6 with Spring Security and Spring Data JDBC
- **Database:** PostgreSQL with Flyway migrations
- **Build:** Gradle with Kotlin DSL
- **Testing:** JUnit 5 with Testcontainers for integration tests

**Key Dependencies:**
- Spring Boot Starter Web, Security, Data JDBC
- Flyway for database migrations
- Jackson for JSON with Kotlin module
- Testcontainers with PostgreSQL for testing

**Package Structure:**
- Main application: `me.soenke.spring_demo.SpringDemoApplication`
- Test configuration uses Testcontainers with PostgreSQL 16 Alpine
- Database migrations stored in `src/main/resources/db/migration/`

**Testing Setup:**
- Tests use Testcontainers for real PostgreSQL database
- `TestContainerConfig` provides a PostgreSQL container with test database
- Test database: `testdb` with credentials `test/test`

**Development Notes:**
- Uses Spring Boot DevTools for development
- Application properties minimal (just application name)
- Ready for JDBC-based data access patterns
- Migration scripts should follow Flyway naming: `V{number}__{description}.sql`