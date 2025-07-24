# CLAUDE.md

## Commands

```bash
./gradlew build          # Build project and run tests
./gradlew test           # Run tests only
./gradlew bootRun        # Run the application locally
./gradlew flywayMigrate  # Run database migrations
```

## Stack

- **Language:** Kotlin with Java 21
- **Framework:** Spring Boot with Spring Security and Spring Data JDBC
- **Database:** PostgreSQL with Flyway migrations
- **Testing:** Kotest with Testcontainers

## Architecture

- **Main class:** `me.soenke.spring_demo.SpringDemoApplication`
- **Data access:** Spring Data JDBC with `CrudRepository` pattern
- **Migrations:** `src/main/resources/db/migration/V{number}__{description}.sql`
- **Test setup:** Testcontainers with PostgreSQL via `TestContainerConfig`

## Coding Style

- **Kotlin idioms:** Use data classes, extension functions, null safety
- **Naming:** PascalCase classes, camelCase functions, SCREAMING_SNAKE_CASE constants
- **Structure:** Group by feature, not layer; keep files under 300 lines
- **Logging:** Structured messages with placeholders
- **Spring:** Constructor injection, configuration as code

## Testing

Uses Kotest "Should Spec" style:

```kotlin
class MyServiceTest : ShouldSpec({
    context("MyService") {
        should("perform expected operation") {
            result shouldBe expectedValue
        }
    }
})
```

Integration tests:
```kotlin
@SpringBootTest
@Import(TestContainerConfig::class)
@ActiveProfiles("test")
class MyIntegrationTest(val myService: MyService) : ShouldSpec({
    context("integration") {
        should("work with database") { /* test code */ }
    }
})
```
