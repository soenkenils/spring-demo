package me.soenke.spring_demo

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

/**
 * Controller for managing name submissions.
 */
@RestController
class NameController(private val nameService: NameService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    /**
     * Creates a new name entry.
     *
     * @param request The name submission request
     * @return Response indicating success or failure
     */
    @PostMapping("/names")
    fun createName(@Valid @RequestBody request: NameRequest): ResponseEntity<NameResponse> {
        logger.info("Received name creation request: {}", mapOf("name" to request.name))

        return when (val result = nameService.createName(request.name)) {
            is Result.Success -> {
                logger.info("Successfully created name: {}", mapOf("name" to request.name))
                ResponseEntity.status(HttpStatus.CREATED).body(NameResponse(request.name, "Name created successfully"))
            }
            is Result.Failure -> {
                logger.warn("Failed to create name: {}", mapOf(
                    "name" to request.name,
                    "reason" to result.error
                ))

                if (result.error == "Name already exists") {
                    ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(NameResponse(request.name, "Name already exists"))
                } else {
                    ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(NameResponse(request.name, result.error))
                }
            }
        }
    }
}

/**
 * Request data for name creation.
 */
data class NameRequest(
    @field:NotBlank(message = "Name cannot be empty")
    val name: String
)

/**
 * Response data for name operations.
 */
data class NameResponse(
    val name: String,
    val message: String
)

/**
 * Generic result type for operations that can fail.
 */
sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure(val error: String) : Result<Nothing>()
}
