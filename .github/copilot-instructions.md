# GitHub Copilot Instructions

## Tools and Frameworks

- JVM: The project is built using Java 21, and the code should be compatible with this version.
- Spring Boot: The project follows Spring Boot conventions for dependency injection and service layers.
- Gradle for Dependency Management: Dependencies are managed using Gradle with Kotlin DSL.
- Kotest for Testing: Tests are written using the "Should Spec" style, aligning with the team's preference.
- Our team uses Jira for tracking items of work.

## Coding Style

Structured Logging: Logging is done with structured messages using placeholders (e.g., logger.info("Message with ID={}", id)).

Kotlin Idioms: Prefer Kotlin's built-in features over Java equivalents. Use data classes, extension functions, and null safety features.

Naming Conventions:
- Classes: PascalCase (e.g., `UserService`)
- Functions and properties: camelCase (e.g., `getUserById`, `userId`)
- Constants: SCREAMING_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- Test functions: use descriptive phrases with backticks (e.g., `` `should return user when valid ID is provided` ``)

Code Organization:
- Group related code in packages by feature, not by layer
- Keep file size manageable (under 300 lines when possible)
- One class per file unless they are tightly coupled inner classes

Error Handling:
- Include detailed error messages with context for debugging
- Always properly close resources using `use` blocks

Documentation:
- Add KDoc comments for public APIs and complex functions
- Include examples in documentation for non-trivial functions
- Document non-obvious design decisions inline

Spring Conventions:
- Prefer constructor injection over field injection
- Use Spring's functional programming model where appropriate
- Follow the principle of "configuration as code" for Spring configurations

## Testing
- Use Kotest for unit and integration tests
- Write tests in the same package as the code being tested
- Use descriptive names for test cases
- Group related tests in "should" blocks
- Run tests with Gradle using the `test` task, e.g., `./gradlew test --tests MyClassNameTest`
- Use `@SpringBootTest` for integration tests to load the full application context
- Consider docs/testing.md for more details on testing practices

### Testing with Kotest

This project uses [Kotest](https://kotest.io/) as the testing framework with the "Should Spec" style.

#### Example Test Structure

```kotlin
class MyServiceTest : ShouldSpec({
    
    context("MyService") {
        should("perform expected operation when condition is met") {
            // Test code here
            result shouldBe expectedValue
        }
        
        should("handle edge case properly") {
            // Test code here
            result shouldBe expectedValue
        }
    }
    
})
```

#### Integration with Spring Boot

For tests that require the Spring context, use the following configuration:

```kotlin
@SpringBootTest
@Import(TestContainerConfig::class)  // If database access is needed
@ActiveProfiles("test")
class MyIntegrationTest(val myService: MyService) : ShouldSpec({

    context("MyService integration") {
        should("interact with database correctly") {
            // Test code here
        }
    }
    
})
```

#### Test Types

1. **Unit Tests**: Simple tests that don't require Spring context
2. **Integration Tests**: Tests that use the Spring context and may use TestContainers for database access

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific tests
./gradlew test --tests "MyIntegrationTest"
```
