# Spring Boot Kotlin Patterns

## Description

Guidelines for writing idiomatic Spring Boot code with Kotlin following the patterns established in this project.

**Use this skill when:**
- Creating new controllers, services, or repositories
- Configuring Spring components
- Working with dependency injection
- Implementing data transfer objects (DTOs)

## Core Principles

### 1. Constructor Injection (Never Field Injection)

**Good:**
```kotlin
@RestController
class UserController(
    private val userService: UserService
)
```

**Bad:**
```kotlin
@RestController
class UserController {
    @Autowired
    private lateinit var userService: UserService
}
```

### 2. Data Classes for DTOs and Responses

```kotlin
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String
)
```

### 3. Feature-Based Package Structure

```
src/main/kotlin/me/soenke/spring_demo/
├── config/           # Configuration classes
├── dadjoke/          # Dad jokes feature
│   ├── DadJokeController.kt
│   ├── model/
│   └── repository/
├── greeting/         # Greetings feature
├── name/             # Names feature
└── weather/          # Weather feature
```

Group by feature, not by layer (no separate `controllers/`, `services/`, `repositories/` directories).

### 4. Structured Logging

**Good:**
```kotlin
logger.info("Serving random dad joke. ID={}, Length={}", randomJoke.id, randomJoke.jokeText.length)
```

**Bad:**
```kotlin
logger.info("Joke: $randomJoke")
```

Always use placeholders instead of string interpolation in log messages.

### 5. Spring Data JDBC with CrudRepository

```kotlin
@Repository
interface DadJokeRepository : CrudRepository<DadJoke, Long> {
    @Query("SELECT * FROM dad_jokes ORDER BY RANDOM() LIMIT 1")
    fun findRandomJoke(): DadJoke?
}
```

### 6. Exception Handling with ResponseStatusException

```kotlin
@GetMapping("/dad-jokes")
fun getRandomDadJoke(): ResponseEntity<DadJokeResponse> {
    val joke = repository.findRandomJoke()
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No jokes found")

    return ResponseEntity.ok(DadJokeResponse(joke.text))
}
```

### 7. Kotlin Idioms

- Use `data class` for immutable DTOs
- Use `?.let` or Elvis operator `?:` for null handling
- Prefer `val` over `var`
- Use extension functions for utility logic
- Embrace null safety (no nullability annotations needed with proper types)

### 8. File Size Limit

Keep files under 300 lines. Split into multiple files when a class exceeds this limit.

### 9. Configuration as Code

Use `@Configuration` and `@Bean` for Spring configuration instead of XML or properties.

```kotlin
@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // Configuration here
    }
}
```

## Common Patterns

### RESTful Endpoints

```kotlin
@RestController
@RequestMapping("/endpoint")
class MyController(private val service: MyService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<ResponseDTO>> {
        return ResponseEntity.ok(service.getAll())
    }

    @PostMapping
    fun create(@RequestBody request: CreateRequest): ResponseEntity<ResponseDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request))
    }
}
```

### Repository Pattern

```kotlin
@Repository
interface UserRepository : CrudRepository<User, Long> {
    @Query("SELECT * FROM users WHERE email = :email")
    fun findByEmail(@Param("email") email: String): User?
}
```

### Service Layer

```kotlin
@Service
class UserService(private val repository: UserRepository) {

    fun getUserByEmail(email: String): UserResponse {
        val user = repository.findByEmail(email)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        return user.toResponse()
    }
}
```

## Import Organization

1. Standard library imports
2. Third-party imports
3. Project imports (blank line between each)

```kotlin
import java.time.LocalDateTime
import kotlin.math.max

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.http.ResponseEntity

import me.soenke.spring_demo.dadjoke.DadJokeResponse
```
