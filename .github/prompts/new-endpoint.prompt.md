---
title: "New HTTP Endpoint"
description: "Creates a new HTTP endpoint in the project."
tags: ["http", "endpoint", "restful"]
---
# New HTTP Endpoint

Your goal is to generate a new HTTP endpoint.

Add the new HTTP endpoint in a new file in the project. The file should be named according to the endpoint's purpose and follow the project's naming conventions.

A typical file for a new HTTP endpoint in a Spring Boot project would look like this:

```kotlin
package me.soenke.spring_demo

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EndpointController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{{endpointUrl}}") // Replace with the actual endpoint URL
    fun endpoint(): ResponseEntity<EndpointResponse> {
        logger.info("Received request for ...")
        return ResponseEntity.ok(EndpointResponse(name="Hello, World!"))
    }
}

data class EndpointResponse(val name: String)
```

The endpoint should be implemented in a new file in the project. The file should be named according to the endpoint's purpose and follow the project's naming conventions.
Determine the package name based on the project's structure and the endpoint's purpose.

The endpoint DTO models should be placed in the same file as the endpoint.

Ask for the following before you start:
- The purpose of the endpoint
- The HTTP method (GET, POST, PUT, DELETE)  
- The endpoint URL
- The request body (if applicable) 
- The response body
- The authentication method (if applicable)
- The error handling (if applicable)
- The validation rules (if applicable)

Requirements for the HTTP endpoint:
- The endpoint should be implemented in Kotlin and follow the best practices for Spring Boot applications.
- The endpoint should be RESTful and follow best practices.
- The endpoint should be secure and handle authentication properly.
- The endpoint should be well-documented with clear descriptions of the request and response bodies.
- The endpoint should handle errors gracefully and return appropriate status codes.
- The endpoint should validate the request body and return appropriate error messages if validation fails.
- The endpoint should be tested (unit- and integration tests) and verified to work as expected.

Use the `/new-endpoint` prompt to guide the creation of new HTTP endpoints.
