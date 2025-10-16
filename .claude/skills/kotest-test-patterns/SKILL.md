# Kotest Test Patterns

## Description

Testing standards using Kotest "Should Spec" style for unit and integration tests in this Spring Boot Kotlin project.

**Use this skill when:**
- Writing new tests
- Updating existing tests
- Creating integration tests
- Creating unit tests for services

## Test Structure

### Unit Tests

Unit tests don't require Spring context and should be fast:

```kotlin
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldThrow

class ExampleServiceTest : ShouldSpec({

    context("ExampleService") {

        should("perform operation when condition is met") {
            val service = ExampleService()
            val result = service.doSomething("input")
            result shouldBe "expected output"
        }

        should("throw exception for invalid input") {
            val service = ExampleService()
            shouldThrow<IllegalArgumentException> {
                service.doSomething(null)
            }
        }

        should("handle edge cases correctly") {
            val service = ExampleService()
            val result = service.doSomething("")
            result shouldBe emptyList()
        }
    }
})
```

### Integration Tests with Spring Context

Integration tests that require database or Spring context:

```kotlin
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.junit.jupiter.api.TestInstance
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldNotBe
import me.soenke.spring_demo.config.TestDatabaseConfig

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = [TestDatabaseConfig::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DadJokeControllerTest @Autowired constructor(
    private val mockMvc: MockMvc
) : ShouldSpec({

    context("GET /dad-jokes") {

        should("return a random dad joke with 200 OK") {
            val response = mockMvc.get("/dad-jokes") {
                header("Content-Type", "application/json")
            }.andExpect {
                status { isOk() }
                content { contentType("application/json") }
                jsonPath("$.joke") {
                    exists()
                    isString()
                }
            }.andReturn()
                .response.contentAsString

            val jokeResponse = objectMapper.readValue<Map<String, String>>(response)
            jokeResponse["joke"] shouldNotBe null
        }

        should("return different jokes on subsequent calls") {
            val jokes = mutableSetOf<String>()

            repeat(10) {
                val response = mockMvc.get("/dad-jokes")
                    .andExpect { status { isOk() } }
                    .andReturn()
                    .response.contentAsString

                val jokeResponse = objectMapper.readValue<Map<String, String>>(response)
                jokeResponse["joke"]?.let { jokes.add(it) }
            }

            assert(jokes.size > 1)
        }
    }
})
```

## Test Naming Conventions

**Format:** `should("[action] [when condition]")`

```kotlin
// Good
should("return user when ID exists")
should("throw exception when email is invalid")
should("update record and return new value")

// Bad
should("test getUser")
should("works correctly")
should("test exception handling")
```

## Assertions

Use Kotest matchers instead of JUnit assertions:

```kotlin
// Good
result shouldBe expectedValue
value shouldNotBe null
list.shouldContain(item)
exception.shouldHaveMessage("error message")

// Bad
assertEquals(result, expectedValue)
assertNotNull(value)
assertTrue(list.contains(item))
```

## Mocking with MockK

Use MockK for mocking dependencies:

```kotlin
class UserServiceTest : ShouldSpec({

    context("UserService") {
        val mockRepository = mockk<UserRepository>()
        val service = UserService(mockRepository)

        should("call repository and return user") {
            val user = User(1, "John", "john@example.com")
            every { mockRepository.findById(1) } returns user

            val result = service.getUser(1)

            result shouldBe user
            verify { mockRepository.findById(1) }
        }

        should("throw exception when repository fails") {
            every { mockRepository.findById(99) } throws EntityNotFoundException()

            shouldThrow<EntityNotFoundException> {
                service.getUser(99)
            }
        }
    }
})
```

## Test Organization

### By Feature Context

```kotlin
class OrderServiceTest : ShouldSpec({

    context("Creating orders") {
        should("create order with valid data")
        should("reject order with invalid data")
    }

    context("Retrieving orders") {
        should("return all orders")
        should("return order by ID")
    }

    context("Updating orders") {
        should("update order status")
        should("prevent updates to completed orders")
    }
})
```

## Kotest Matchers Reference

```kotlin
// Equality
value shouldBe expected
value shouldNotBe expected

// Nullability
value.shouldBeNull()
value.shouldNotBeNull()

// Ranges
value.shouldBeGreaterThan(0)
value.shouldBeLessThan(100)
value.shouldBeBetween(0, 100)

// Collections
list.shouldBeEmpty()
list.shouldNotBeEmpty()
list.shouldContain(item)
list.shouldHaveSize(5)
list.shouldStartWith(item)

// Strings
string.shouldBeEmpty()
string.shouldContain("substring")
string.shouldStartWith("prefix")
string.shouldEndWith("suffix")
string.shouldMatch("regex.*pattern")

// Exceptions
shouldThrow<ExceptionType> { /* code */ }
```

## Setup and Teardown

```kotlin
class ServiceTest : ShouldSpec({

    // Test data setup
    val mockRepo = mockk<Repository>()
    val service = Service(mockRepo)

    beforeTest {
        // Runs before each test
        clearAllMocks()
    }

    afterTest {
        // Runs after each test
        // Cleanup
    }

    context("Service") {
        should("do something")
    }
})
```

## Integration Test Database Setup

Use TestContainers with PostgreSQL:

**In tests:**
```kotlin
@SpringBootTest
@Import(TestContainerConfig::class)
@ActiveProfiles("test")
class MyIntegrationTest : ShouldSpec({
    // Database is automatically started
})
```

**TestContainerConfig:**
```kotlin
@TestConfiguration
class TestContainerConfig {
    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
    }
}
```

## Test Execution

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "DadJokeControllerTest"

# Run specific test method
./gradlew test --tests "DadJokeControllerTest.should*"
```

## Code Coverage

```bash
# Run tests with coverage
./gradlew test jacocoTestReport

# View HTML report
open build/reports/jacoco/test/html/index.html
```

## Best Practices

1. **One assertion focus per test** - Each test should verify one behavior
2. **Descriptive names** - Test names should explain what's being tested
3. **DRY setup** - Use `beforeTest` for common setup
4. **Avoid test interdependence** - Tests should be independent
5. **Fast execution** - Prefer unit tests to integration tests
6. **Isolate external calls** - Mock external services, APIs, databases (except when testing integration)
7. **Test both success and failure** - Include edge cases and error scenarios
