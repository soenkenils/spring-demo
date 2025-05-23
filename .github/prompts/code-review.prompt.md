---
title: "Spring Boot RestController Endpoint Review"
description: "Reviews Spring Boot RestController endpoints for best practices and security"
tags: ["spring-boot", "kotlin", "review", "security"]
---

# Spring Boot REST-Endpoint Review

Review the selected Spring Boot REST endpoint in #selection or #file.

## Check for:

### Kotlin Usage
- Idiomatic Kotlin code (data classes, null safety, extension functions)
- Proper use of sealed classes or Result<T> for error handling
- No platform types or Java-style code

### Spring Boot Best Practices
- Use of @RestController and @RequestMapping annotations
- Constructor-based dependency injection
- Proper HTTP status codes and response entities
- Consistent API response format (e.g., wrapping responses)
- Use of DTOs for request/response bodies
- Grouping endpoints by feature

### Security
- Input validation (e.g., @Valid, custom validators)
- Safe error messages (no sensitive info leakage)
- CSRF protection (where applicable)
- Proper authentication and authorization checks
- Secure HTTP headers (e.g., via Spring Security)

### Testing
- Unit test coverage
- Integration test setup
- Edge case handling

## Output Format

Provide findings in these categories:
- 🔴 Critical Issues
- 🟡 Improvements
- 🟢 Good Practices
