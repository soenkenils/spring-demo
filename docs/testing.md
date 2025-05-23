# Spring Demo with Kotest

This project is a Spring Boot application built with Kotlin and configured for testing with Kotest.

## Testing with Kotest

This project uses [Kotest](https://kotest.io/) as the testing framework with the "Should Spec" style.

### Example Test Structure

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

### Integration with Spring Boot

For tests that require the Spring context, use the following configuration:

```kotlin
@SpringBootTest
@Import(TestContainerConfig::class)  // If database access is needed
@ActiveProfiles("test")
class MyIntegrationTest : ShouldSpec({
    
    // Autowire dependencies
    val myService by autowired<MyService>()
    
    context("MyService integration") {
        should("interact with database correctly") {
            // Test code here
        }
    }
    
})
```

### Test Types

1. **Unit Tests**: Simple tests that don't require Spring context
2. **Integration Tests**: Tests that use the Spring context and may use TestContainers for database access

## Running Tests

```bash
# Run all tests
./gradlew test

# Run specific tests
./gradlew test --tests "me.soenke.spring_demo.example.*"
```

## Test Reports

Test reports can be found at:
- HTML: `build/reports/tests/test/index.html`
