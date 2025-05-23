# GitHub Copilot Guide

*A practical guide to accelerate development with AI-powered coding assistance*.

## Quick Start: Why Copilot Matters for Our Team

GitHub Copilot isn't just another tool—it's a productivity multiplier that can:

- **Reduce boilerplate code** writing by 40-60%
- **Accelerate API integration** development
- **Improve code consistency** across the team
- **Enable faster prototyping** and experimentation

## Table of Contents

1. [Getting Started in 5 Minutes](#getting-started-in-5-minutes)
2. [Essential Features You'll Use Daily](#essential-features-youll-use-daily)
3. [Team Standards & Best Practices](#team-standards--best-practices)
4. [Real-World Examples](#real-world-examples)
5. [Advanced Techniques](#advanced-techniques)
6. [Security & Privacy](#security--privacy)
7. [IDE Setup Guide](#ide-setup-guide)
8. [Troubleshooting & FAQ](#troubleshooting--faq)

---

## Getting Started in 5 Minutes

### Three Ways to Use Copilot

1. **Ghost Text (Auto-completion)**
   - Just start typing—suggestions appear automatically
   - Accept with `Tab`, reject with `Esc`
   - Perfect for: Writing functions, loops, common patterns

2. **Inline Chat** (`Cmd+I` / `Ctrl+I`)
   - Quick edits without leaving your code
   - Highlight code and ask for changes
   - Perfect for: Refactoring, fixing bugs, adding features

3. **Chat Window**
   - Full conversations about architecture and design
   - Ask complex questions, get detailed explanations
   - Perfect for: Learning, debugging, planning

### Your First Copilot Experience

Try this right now:

```kotlin
// Type this comment and press Enter:
// Create a function to validate email addresses

// Copilot will suggest the implementation
```

---

## Essential Features You'll Use Daily

### 1. Slash Commands - Your Productivity Shortcuts

Instead of typing long prompts, use these built-in commands:

| Command | What it does | When to use | IDE Support |
|---------|--------------|-------------|-------------|
| `/explain` | Explains selected code | Understanding legacy code | ✅ All IDEs |
| `/fix` | Fixes bugs in selection | Quick debugging | ✅ All IDEs |
| `/tests` | Generates unit tests | Increasing coverage | ✅ All IDEs |
| `/doc` | Creates documentation | API documentation | ✅ All IDEs |
| `/help` | Shows available commands | Learning commands | ✅ All IDEs |
| `/new` | Creates new files | Starting fresh | ⚠️ VS Code only |
| `/review` | Reviews code quality | Before PR submission | ⚠️ Limited in IntelliJ |

**Example:**

```markdown
Select your function, then type:
/tests including edge cases and error scenarios
```

**Note:** IntelliJ supports basic slash commands, but some advanced features may be limited compared to VS Code.

### 2. Context References - Point Copilot to the Right Information

| Reference | Purpose | Example |
|-----------|---------|---------|
| `@workspace` | Search entire project | `@workspace find all REST clients` |
| `#file` | Reference specific file | `#file:UserService.kt` |
| `#selection` | Current selection | `Refactor #selection to use coroutines` |

### 3. The 3S Principle for Better Results

**Simple • Specific • Short**:

❌ **Don't do this:**:

```markdown
Create a complete integration system with authentication, error handling, retry logic, monitoring, and reporting
```

✅ **Do this instead:**:

```markdown
1. Create a REST client with authentication
2. Add retry logic with exponential backoff
3. Include error handling for common HTTP errors
```

---

## Team Standards & Best Practices

### Setting Up Repository Standards

Create `.github/copilot-instructions.md` to ensure consistent code generation:

```markdown
# Team Copilot Instructions

## Technology Stack
- Backend: Kotlin with Spring Boot
- Database: PostgreSQL with JDBC (no ORM)
- Testing: Kotest with property-based testing
- Build: Gradle Kotlin DSL

## Coding Standards
- Use UUID for all primary keys
- Database tables use snake_case
- All API responses follow our standard format
- Include correlation IDs for tracing
- Wrap database operations in transactions

## Integration Patterns
- Use RestTemplate for HTTP calls
- Implement circuit breakers for external services
- Add comprehensive logging for debugging
- Include retry mechanisms with backoff
- Cache responses where appropriate

## Testing Requirements
- Minimum 80% code coverage
- Mock all external service calls
- Integration tests for all endpoints
- Property-based tests for data validation
```

### Creating Reusable Prompts

**⚠️ Note:** This feature is only available in VS Code. IntelliJ users should refer to the [IDE Setup Guide](#ide-setup-guide) for alternative approaches.

Store common tasks in `.github/prompts/`:

**Example: `api-integration.prompt.md`**

```markdown
---
title: "API Integration Generator"
description: "Creates robust API client implementations"
---

Generate a Spring Boot service to integrate with {{api_name}}:

## Requirements:
- RestTemplate-based HTTP client
- Proper authentication handling
- Circuit breaker pattern
- Retry logic with exponential backoff
- Comprehensive error handling

## Implementation Details:
- Request/response DTOs with validation
- Logging for debugging
- Timeout configuration
- Response caching where applicable

Variables:
- API Name: {{api_name}}
- Authentication Type: {{auth_type}}
- Base URL: {{base_url}}
```

---

## Real-World Examples

### Example 1: API Integration

```kotlin
// Prompt: Create a REST client with OAuth2 authentication and retry logic

@Service
class PartnerApiClient(
    private val restTemplate: RestTemplate,
    private val tokenService: TokenService
) {
    // Copilot will generate the integration code
}
```

### Example 2: Data Validation

```kotlin
// Using inline chat: 
// "Add comprehensive validation for incoming webhook data"

// Select your webhook handler and press Cmd+I
```

### Example 3: Error Handling

```kotlin
// Chat window prompt:
// "Create a global exception handler for our REST APIs 
// that returns standardized error responses"
```

### Example 4: Integration Testing

```markdown
/tests Create integration tests that:
- Mock external API responses
- Test error scenarios
- Verify retry behavior
- Check timeout handling
```

---

## Advanced Techniques

### Custom Instructions for Your Tech Stack

**For Spring Boot + Kotlin Development:**

```kotlin
// Effective context for service integration
@Service
class IntegrationService(
    private val restTemplate: RestTemplate,
    private val jdbcTemplate: JdbcTemplate
) {
    // Ask: "Create a method to sync data from external API"
}

// For data mapping
data class ExternalApiResponse(
    val id: String,
    val status: String,
    val data: Map<String, Any>
)
// Ask: "Map this to our internal domain model with validation"
```

### Intelligent Prompting Strategies

#### 1. Q&A Approach

```markdown
"I need to implement webhook authentication. 
Ask me questions to understand our security requirements."
```

#### 2. Pros/Cons Analysis

```markdown
"Compare using Redis vs in-memory caching 
for API response caching in our system"
```

#### 3. Step-by-Step Implementation

```markdown
"Help me implement API rate limiting:
1. Design the rate limit strategy
2. Create the implementation
3. Add monitoring and alerts
4. Write comprehensive tests"
```

### Working with External APIs

**Effective Prompts for Integration:**

```markdown
"Create a resilient HTTP client that handles:
- Connection timeouts
- Rate limiting (429 responses)
- Automatic retries with jitter
- Circuit breaking after failures"
```

**For Webhook Handling:**

```markdown
"Generate a webhook controller that:
- Validates signatures
- Handles duplicate deliveries
- Processes asynchronously
- Returns proper status codes"
```

---

## Security & Privacy

### What Copilot Sees and Stores

**Copilot CAN see:**

- Your active code and comments
- File names and project structure
- Open files in your IDE

**Copilot does NOT store:**

- Your proprietary code (with enterprise settings)
- Sensitive data or secrets
- Generated outputs

### Security Best Practices

1. **Never include in prompts:**
   - Real API keys or credentials
   - Customer data
   - Internal system details
   - Production URLs or endpoints

2. **Always review generated code for:**
   - Proper input validation
   - Secure credential handling
   - SQL injection prevention
   - Appropriate error messages

3. **Configure exclusions:**

   ```text
   # .github/copilot-ignore
   **/credentials/**
   **/secrets/**
   **/*.key
   **/*.pem
   **/application-prod.yml
   ```

### Handling Sensitive Integrations

**Do:**

- Use generic names in prompts ("external API", "partner service")
- Focus on patterns rather than specific implementations
- Review all generated authentication code

**Don't:**

- Include actual API endpoints in prompts
- Share partner-specific implementation details
- Copy production configurations

---

## IDE Setup Guide

### IntelliJ IDEA (Recommended for Kotlin)

**Installation:**

1. File → Settings → Plugins
2. Search "GitHub Copilot"
3. Install and restart
4. Sign in with GitHub account

**Key Features Available:**

- ✅ Code completion
- ✅ Chat interface
- ✅ Custom instructions
- ❌ Shared prompts (use workaround below)
- ❌ Agent mode

**IntelliJ Workaround for Shared Prompts:**

```shell
# Create .idea/copilot-templates/
├── api-client.md
├── webhook-handler.md
├── integration-tests.md
└── error-handling.md

# Copy/paste templates as needed
```

### VS Code (For Advanced AI Features)

**When to use VS Code:**

- Creating documentation
- Large refactoring projects
- Using agent mode
- Accessing shared prompts

**Dual IDE Strategy:**

- Use IntelliJ for daily Kotlin development
- Switch to VS Code for AI-intensive tasks
- Keep both configured with same formatting rules

---

## Troubleshooting & FAQ

### Common Issues

**Q: Copilot suggests outdated library versions**:

- A: Specify versions in your prompt: "Use Spring Boot 3.2 with Kotlin 1.9"

**Q: Generated code doesn't match our patterns**:

- A: Update `.github/copilot-instructions.md` with specific examples

**Q: Copilot is slow or unresponsive**:

- A: Check network connection, restart IDE, clear caches

### Best Practices for API Integration

1. **Provide Context:**

   ```kotlin
   // Good: Specific about requirements
   // Create a REST client with OAuth2, retry logic, and circuit breaker
   
   // Bad: Too vague
   // Make an API call
   ```

2. **Use Existing Patterns:**

   ```markdown
   Reference our existing #file:UserApiClient.kt 
   to create similar integration for orders
   ```

3. **Test Generation:**

   ```markdown
   /tests with mocked responses including:
   - Success scenarios
   - 4xx and 5xx errors
   - Timeout cases
   - Malformed responses
   ```

### Getting Help

- **Internal**: Share successful prompts in our team channel
- **GitHub**: Check Copilot documentation
- **Community**: Stack Overflow `github-copilot` tag

---

## Next Steps

1. **Install Copilot** in your IDE today
2. **Try the examples** in this guide
3. **Share your experience** with the team
4. **Contribute** your own tips and patterns

Remember: Copilot is a tool to amplify your expertise, not replace it. Use it to handle repetitive tasks so you can focus on solving complex integration challenges.
