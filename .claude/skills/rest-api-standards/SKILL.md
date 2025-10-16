# REST API Standards

## Description

REST API design standards and best practices for this Spring Boot project, ensuring consistency across endpoints.

**Use this skill when:**
- Creating new REST endpoints
- Designing API responses
- Handling errors
- Planning HTTP status codes

## RESTful Design Principles

### 1. Resource-Based URLs

**Good:**
```
GET    /users              # List all users
GET    /users/{id}         # Get specific user
POST   /users              # Create user
PUT    /users/{id}         # Update user
DELETE /users/{id}         # Delete user
```

**Bad:**
```
GET    /getUserList
POST   /createUser
GET    /user/details
DELETE /removeUser
```

### 2. HTTP Methods

| Method | Purpose | Idempotent | Safe |
|--------|---------|-----------|------|
| GET | Retrieve resource | Yes | Yes |
| POST | Create resource | No | No |
| PUT | Replace resource | Yes | No |
| PATCH | Partial update | No | No |
| DELETE | Remove resource | Yes | No |

### 3. HTTP Status Codes

**Success Responses:**
- `200 OK` - Request successful, returning data
- `201 Created` - Resource created successfully
- `202 Accepted` - Request accepted for async processing
- `204 No Content` - Request successful, no content to return

**Redirection:**
- `301 Moved Permanently`
- `302 Found`
- `304 Not Modified`

**Client Errors:**
- `400 Bad Request` - Invalid request data
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Authenticated but not authorized
- `404 Not Found` - Resource doesn't exist
- `409 Conflict` - Conflict (e.g., duplicate)
- `422 Unprocessable Entity` - Validation failed

**Server Errors:**
- `500 Internal Server Error` - Unexpected error
- `502 Bad Gateway` - External service error
- `503 Service Unavailable` - Temporarily down

## Endpoint Design

### Basic CRUD Endpoints

```kotlin
@RestController
@RequestMapping("/jokes")
class JokeController(
    private val jokeService: JokeService
) {

    @GetMapping
    fun getAllJokes(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): ResponseEntity<Page<JokeResponse>> {
        return ResponseEntity.ok(jokeService.getAll(page, pageSize))
    }

    @GetMapping("/{id}")
    fun getJokeById(
        @PathVariable id: Long
    ): ResponseEntity<JokeResponse> {
        return ResponseEntity.ok(jokeService.getById(id))
    }

    @PostMapping
    fun createJoke(
        @Valid @RequestBody request: CreateJokeRequest
    ): ResponseEntity<JokeResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(jokeService.create(request))
    }

    @PutMapping("/{id}")
    fun updateJoke(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateJokeRequest
    ): ResponseEntity<JokeResponse> {
        return ResponseEntity.ok(jokeService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun deleteJoke(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        jokeService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
```

## Request/Response Objects

### DTOs (Data Transfer Objects)

```kotlin
// Request DTO - for receiving data from client
data class CreateJokeRequest(
    @NotBlank(message = "Joke text cannot be blank")
    val text: String,

    @Email(message = "Author email must be valid")
    val author: String?
)

// Response DTO - for sending data to client
data class JokeResponse(
    val id: Long,
    val text: String,
    val author: String?,
    val createdAt: LocalDateTime
)

// Update Request DTO
data class UpdateJokeRequest(
    val text: String?,
    val author: String?
)
```

### Validation

Use Jakarta Bean Validation (previously javax.validation):

```kotlin
import jakarta.validation.constraints.*

data class UserRequest(
    @NotBlank(message = "Name is required")
    val name: String,

    @Email(message = "Valid email required")
    val email: String,

    @Min(value = 18, message = "Must be at least 18")
    @Max(value = 120, message = "Invalid age")
    val age: Int,

    @Size(min = 8, max = 100, message = "Password must be 8-100 characters")
    val password: String
)
```

## Error Handling

### Standardized Error Response

```kotlin
data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String?,
    val path: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
```

### Global Exception Handler

```kotlin
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNotFoundException(
        ex: NoHandlerFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            message = "Resource not found",
            path = request.getDescription(false).removePrefix("uri=")
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }

        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "Validation failed: $errors",
            path = request.getDescription(false).removePrefix("uri=")
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = "An unexpected error occurred",
            path = request.getDescription(false).removePrefix("uri=")
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
```

### Throwing Errors from Service

```kotlin
@Service
class JokeService(private val repository: JokeRepository) {

    fun getById(id: Long): JokeResponse {
        val joke = repository.findById(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Joke with ID $id not found"
            )
        return joke.toResponse()
    }

    fun create(request: CreateJokeRequest): JokeResponse {
        val existingJoke = repository.findByText(request.text)
        if (existingJoke != null) {
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "Joke with this text already exists"
            )
        }
        val joke = Joke(text = request.text, author = request.author)
        val saved = repository.save(joke)
        return saved.toResponse()
    }
}
```

## Pagination and Filtering

### Pagination

```kotlin
@GetMapping
fun listJokes(
    @RequestParam(defaultValue = "0") page: Int,
    @RequestParam(defaultValue = "10") pageSize: Int,
    @RequestParam(defaultValue = "createdAt") sortBy: String,
    @RequestParam(defaultValue = "DESC") sortDirection: String
): ResponseEntity<Page<JokeResponse>> {
    val pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(sortDirection), sortBy)
    val jokes = jokeService.getAll(pageable)
    return ResponseEntity.ok(jokes.map { it.toResponse() })
}
```

Response format:
```json
{
  "content": [...],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 45,
  "totalPages": 5,
  "last": false,
  "first": true,
  "numberOfElements": 10,
  "empty": false
}
```

### Filtering

```kotlin
@GetMapping
fun searchJokes(
    @RequestParam(required = false) author: String?,
    @RequestParam(required = false) text: String?,
    @RequestParam(defaultValue = "0") page: Int
): ResponseEntity<Page<JokeResponse>> {
    return ResponseEntity.ok(jokeService.search(author, text, page))
}
```

## Content Negotiation

### Accept Headers

```kotlin
@GetMapping(
    produces = [MediaType.APPLICATION_JSON_VALUE, "application/xml"]
)
fun getJoke(): ResponseEntity<JokeResponse> {
    // Client can request JSON or XML via Accept header
}
```

## Versioning (Optional)

If API versioning is needed:

### URL Versioning
```
GET /v1/jokes
GET /v2/jokes
```

### Header Versioning
```
GET /jokes
Accept: application/vnd.myapi.v1+json
```

## Security Headers

Include security headers in responses:

```kotlin
@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/jokes/**").permitAll()
                    .anyRequest().authenticated()
            }
            .headers { headers ->
                headers
                    .contentSecurityPolicy { csp ->
                        csp.policyDirectives("default-src 'self'")
                    }
                    .xssProtection()
                    .frameOptions { frameOptions ->
                        frameOptions.deny()
                    }
            }
        return http.build()
    }
}
```

## API Documentation

Document endpoints with Javadoc comments:

```kotlin
/**
 * Retrieves a random dad joke from the database.
 *
 * @return ResponseEntity containing a JokeResponse with the joke text
 * @throws ResponseStatusException with 404 if no jokes are found
 *
 * Example response:
 * ```
 * {
 *   "id": 1,
 *   "text": "Why don't scientists trust atoms?",
 *   "author": "Unknown",
 *   "createdAt": "2024-01-15T10:30:00"
 * }
 * ```
 */
@GetMapping("/{id}")
fun getJokeById(@PathVariable id: Long): ResponseEntity<JokeResponse>
```

## Response Headers

Include useful headers in responses:

```kotlin
@GetMapping("/{id}")
fun getJoke(@PathVariable id: Long): ResponseEntity<JokeResponse> {
    val joke = jokeService.getById(id)
    return ResponseEntity.ok()
        .header("X-Total-Count", "${jokeService.count()}")
        .header("Cache-Control", "no-cache")
        .body(joke)
}
```

## HATEOAS (Optional)

For more advanced APIs, consider HATEOAS links:

```kotlin
data class JokeResponseWithLinks(
    val id: Long,
    val text: String,
    val _links: Map<String, String>
)

@GetMapping("/{id}")
fun getJoke(@PathVariable id: Long): ResponseEntity<JokeResponseWithLinks> {
    val joke = jokeService.getById(id)
    val links = mapOf(
        "self" to "/jokes/$id",
        "all" to "/jokes",
        "create" to "/jokes"
    )
    return ResponseEntity.ok(JokeResponseWithLinks(joke.id, joke.text, links))
}
```
