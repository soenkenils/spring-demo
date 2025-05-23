# GitHub Copilot Guide

*A practical guide to accelerate development with AI-powered coding assistance*.

## Quick Start: Why Copilot Matters for Our Team

GitHub Copilot isn't just another tool—it's a productivity multiplier that can:

- **Reduce boilerplate code** writing by 40-60%
- **Accelerate API integration** development
- **Improve code consistency** across the team
- **Enable faster prototyping** and experimentation

## Table of Contents

1. [IDE Setup Guide](#ide-setup-guide)
2. [Getting Started in 5 Minutes](#getting-started-in-5-minutes)
3. [Essential Features You'll Use Daily](#essential-features-youll-use-daily)
4. [Team Standards and Best Practices](#team-standards-and-best-practices)
5. [Real-World Examples](#real-world-examples)
6. [Advanced Techniques](#advanced-techniques)
7. [Security and Privacy](#security-and-privacy)
8. [Troubleshooting and FAQ](#troubleshooting-and-faq)

## IDE Setup Guide

### IntelliJ IDEA (Recommended for Kotlin Development)

**Installation:**

1. File → Settings → Plugins
2. Search "GitHub Copilot"
3. Install both plugins:
   - GitHub Copilot
   - GitHub Copilot Chat
4. Restart IntelliJ
5. Sign in with GitHub account

**Available Features:**

- ✅ Code completion (ghost text)
- ✅ Chat interface
- ✅ Inline chat (click Copilot icon in gutter)
- ✅ Custom instructions (`.github/copilot-instructions.md`)
- ✅ Slash commands: `/explain`, `/fix`, `/tests`, `/help`, `/feedback`, `/doc`, `/simplify`
- ❌ Shared prompts (`.prompt.md` files) - not supported
- ❌ Agent mode - not available
- ❌ Context variables (`@workspace`, `#file`) - use attachment button instead

**IntelliJ-Specific Usage:**

- For inline chat: Click the **Copilot icon** next to line numbers (no keyboard shortcut available)
- Use the **attachment button (📎)** to add files to chat context
- Drag and drop files directly into chat
- Keep relevant files open in editor tabs
- Use `/help` to see all available commands

**IntelliJ Workaround for Shared Prompts:**

```
# Create personal template library:
.idea/copilot-templates/
├── api-client.md
├── webhook-handler.md
├── integration-tests.md
└── error-handling.md

# Copy/paste templates into chat as needed
```

### VS Code (For Advanced Copilot Features)

**Installation:**

1. Open Extensions (Ctrl+Shift+X)
2. Search "GitHub Copilot"
3. Install:
   - GitHub Copilot
   - GitHub Copilot Chat
4. Sign in with GitHub account

**Available Features:**

- ✅ All IntelliJ features PLUS:
- ✅ Shared prompts (`.prompt.md` files)
- ✅ Agent mode (preview)
- ✅ Context variables (`@workspace`, `@terminal`, `#file`)
- ✅ Additional slash commands (`/new`, `/terminal`, `/clear`, `/api`)
- ✅ Terminal integration

**When to Use VS Code:**

- Creating shared prompt templates for the team
- Large-scale refactoring with agent mode
- Debugging terminal/CLI issues
- Multi-file operations with `@workspace`
- Creating new project scaffolding with `/new`

### Recommended Dual-IDE Strategy

**Primary (IntelliJ IDEA):**

- Daily Kotlin development
- Debugging and testing
- Code navigation
- Standard Copilot features

**Secondary (VS Code):**

- AI-intensive tasks
- Creating documentation
- Using shared prompts
- Complex multi-file refactoring

**Setup Consistency:**

```bash
# Share these between IDEs:
.editorconfig          # Code style
.github/
├── copilot-instructions.md  # Works in both IDEs
└── prompts/                 # VS Code only
    ├── api-integration.prompt.md
    └── test-generation.prompt.md
```

---

## Getting Started in 5 Minutes

### Three Ways to Use Copilot

1. **Ghost Text (Auto-completion)**
   - Just start typing—suggestions appear automatically
   - Accept with `Tab`, reject with `Esc`
   - Perfect for: Writing functions, loops, common patterns

2. **Inline Chat**
   - **VS Code**: Press `Cmd+I` / `Ctrl+I`
   - **IntelliJ**: Click the Copilot icon next to line numbers
   - Perfect for: Refactoring, fixing bugs, adding features

3. **Chat Window**
   - Full conversations about architecture and design
   - Ask complex questions, get detailed explanations
   - Perfect for: Learning, debugging, planning

### Your First Copilot Experience

Try these examples right now:

**1. Ghost Text (Auto-completion):**

```kotlin
// Type this comment and press Enter:
// Create a function to validate email addresses

// Copilot will suggest the implementation
```

**2. Inline Chat:**

- **VS Code**: Highlight any function and press `Ctrl+I` / `Cmd+I`
- **IntelliJ**: Highlight code and click the Copilot icon in the gutter (next to line numbers)

```kotlin
// Try: "Add error handling and logging"
```

**3. Chat Window:**

- **VS Code**: Press `Ctrl+Alt+I` / `Cmd+Ctrl+I`
- **IntelliJ**: Click the Copilot Chat icon in the sidebar

```
@workspace what's our standard pattern for REST clients?
```

## Essential Features You'll Use Daily

### Keyboard Shortcuts

**Quick Access:**

- **Open Copilot Chat:**
  - VS Code: `Ctrl+Alt+I` (Windows/Linux) or `Cmd+Ctrl+I` (Mac)
  - IntelliJ: Click Copilot Chat icon in sidebar
- **Inline Chat:**
  - VS Code: `Ctrl+I` (Windows/Linux) or `Cmd+I` (Mac)
  - IntelliJ: Click Copilot icon in gutter (next to line numbers)
- **Accept suggestion**: `Tab`
- **Dismiss suggestion**: `Esc`
- **See alternative suggestions**: `Alt+]` or `Alt+[`

### 1. Slash Commands - Your Productivity Shortcuts

Slash commands provide quick access to common tasks:

**Commands Available in IntelliJ IDEA:**

| Command      | What it does                     | Example                          |
|--------------|----------------------------------|----------------------------------|
| `/explain`   | Explains how the code works      | `/explain this authentication flow` |
| `/fix`       | Fix problems and compile errors  | `/fix the null pointer exception` |
| `/tests`     | Generate unit tests              | `/tests with edge cases`         |
| `/help`      | Get help on how to use Copilot chat | `/help`                        |
| `/feedback`  | Steps to provide feedback        | `/feedback`                      |
| `/doc`       | Document the current selection of code | `/doc add KDoc comments`    |
| `/simplify`  | Simplify the code                | `/simplify this complex method`  |

**Commands Available in VS Code:**

| Command         | What it does                     | Example                          |
|------------------|----------------------------------|----------------------------------|
| `/explain`       | Explains how the code works      | `/explain this authentication flow` |
| `/fix`           | Fix problems and compile errors  | `/fix the null pointer exception` |
| `/tests`         | Generate unit tests              | `/tests with edge cases`         |
| `/help`          | Get help on how to use Copilot chat | `/help`                        |
| `/new`           | Scaffolds new code files         | `/new React component with TypeScript` |
| `/newNotebook`   | Creates Jupyter notebook         | `/newNotebook for data analysis` |
| `/api`           | Helps with VS Code extension APIs | `/api how to register a command` |
| `/terminal`      | Explains terminal errors         | `/terminal explain this error`   |
| `/clear`         | Clears current chat thread       | `/clear`                         |

**💡 Pro tip:** Type `/` in chat to see all available commands for your IDE

### 2. Context Variables - Point Copilot to the Right Information

Context variables help Copilot understand what code or information you're asking about:

**VS Code Context Variables:**

| Variable | What it references | Example usage |
|----------|-------------------|---------------|
| `@workspace` | All files in workspace | `@workspace find all API endpoints` |
| `@vscode` | VS Code commands & settings | `@vscode how to debug Kotlin` |
| `@terminal` | Terminal contents | `@terminal what caused this error` |
| `#file` | Specific file | `#file:UserService.kt explain the auth logic` |
| `#selection` | Highlighted code | `optimize #selection for performance` |
| `#codebase` | Indexed workspace | `#codebase where do we handle webhooks` |
| `#editor` | Active editor content | `document methods in #editor` |

**IntelliJ IDEA Context:**

- Use the **attachment button (📎)** to add files to context
- Drag and drop files into the chat
- Reference open editor tabs
- **No support for @ or # syntax** - must use UI controls

**💡 Tips for Better Context:**

- In VS Code: Combine variables like `@workspace #file:config.yml`
- In IntelliJ: Keep relevant files open in tabs before asking questions
- Be specific: "the error handling in #selection" vs just "#selection"

### 3. The 3S Principle for Better Results

**Simple • Specific • Short**:

❌ **Don't do this:**:

```
Create a complete integration system with authentication, error handling, retry logic, monitoring, and reporting
```

✅ **Do this instead:**

```
1. Create a REST client with authentication
2. Add retry logic with exponential backoff
3. Include error handling for common HTTP errors
```

---

## Team Standards and Best Practices

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

```
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

```
"I need to implement webhook authentication. 
Ask me questions to understand our security requirements."
```

#### 2. Pros/Cons Analysis

```
"Compare using Redis vs in-memory caching 
for API response caching in our system"
```

#### 3. Step-by-Step Implementation

```
"Help me implement API rate limiting:
1. Design the rate limit strategy
2. Create the implementation
3. Add monitoring and alerts
4. Write comprehensive tests"
```

#### 4. Debugging Assistance

```
"Explain why this function is causing a null pointer exception 
and suggest a fix."
```

#### 5. Refactoring Suggestions

```
"Refactor this method to improve readability and performance."
```

#### 6. Code Optimization

```
"Optimize this loop to reduce time complexity from O(n^2) to O(n log n)."
```

---

## Security and Privacy

### What Copilot Sees and Stores

**Copilot CAN see:**

- Your active code and comments
- File names and project structure
- Open files in your IDE

**Copilot does NOT store:**

- Your proprietary code (with enterprise settings)
- Sensitive data or secrets
- Generated outputs

**💡 Note:** For organizations, enabling enterprise settings ensures that proprietary code is not stored or used for training Copilot models. Check with your administrator to confirm these settings are active.

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
   # .aiignore
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

## Troubleshooting and FAQ

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

   ```
   Reference our existing #file:UserApiClient.kt 
   to create similar integration for orders
   ```

3. **Test Generation:**

   ```
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
