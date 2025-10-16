# Claude Code Extensions for Spring-Demo

This directory contains Claude Code extensions that enhance development for the Spring Boot + Kotlin project.

## Overview

The extensions are organized into three categories:

- **Skills** - Reusable expertise that Claude applies automatically
- **Sub-Agents** - Specialized AI assistants for specific tasks
- **Hooks** - Automated actions triggered by events

## 📚 Skills

Skills provide domain knowledge that Claude uses automatically when relevant. They encode best practices and patterns.

### Available Skills

#### 1. `spring-boot-kotlin-patterns/`
**When Claude uses it:** When creating or reviewing Spring Boot components

Covers:
- Constructor injection patterns
- Data classes and DTOs
- Feature-based project structure
- Spring annotations best practices
- Error handling with ResponseStatusException
- Kotlin idioms and null safety
- File size and naming conventions

#### 2. `kotest-test-patterns/`
**When Claude uses it:** When writing or updating tests

Covers:
- Kotest "Should Spec" testing style
- Unit tests vs integration tests
- Spring context integration
- MockK for dependency mocking
- Test naming conventions
- Assertions and matchers
- TestContainers for database tests

#### 3. `flyway-migration-patterns/`
**When Claude uses it:** When creating database migrations

Covers:
- Flyway naming conventions
- PostgreSQL best practices
- Table and index creation
- Constraints and foreign keys
- Data migrations
- Migration testing
- Rollback strategies

#### 4. `rest-api-standards/`
**When Claude uses it:** When designing or implementing REST endpoints

Covers:
- RESTful URL design
- HTTP methods and status codes
- Request/response DTOs
- Error handling and validation
- Pagination and filtering
- API documentation
- HATEOAS principles

## 🤖 Sub-Agents

Sub-agents are specialized AI assistants that you can delegate tasks to. They have focused expertise and isolated context.

### Available Sub-Agents

#### 1. `spring-reviewer` - Code Reviewer
**Invoke with:** `@spring-reviewer` or "review this code"

Specializes in:
- Spring Boot pattern review
- Kotlin best practices
- Architecture and design feedback
- REST API standards
- Error handling review
- Code quality assessment

**Example:**
```
@spring-reviewer review my UserController for Spring Boot patterns
```

#### 2. `test-generator` - Test Writer
**Invoke with:** `@test-generator` or "write tests for..."

Specializes in:
- Generating Kotest tests
- Unit test creation
- Integration test setup
- MockK mocking strategies
- Test data factories
- Edge case coverage

**Example:**
```
@test-generator create comprehensive tests for the QuoteService
```

#### 3. `endpoint-builder` - API Builder
**Invoke with:** `@endpoint-builder` or "create new endpoint..."

Specializes in:
- Building complete REST endpoints
- Model/Entity creation
- Repository and Service layers
- Controller implementation
- Database migrations
- Full test suite generation

**Example:**
```
@endpoint-builder create a new /products endpoint with CRUD operations
```

#### 4. `migration-assistant` - Database Specialist
**Invoke with:** `@migration-assistant` or "create migration..."

Specializes in:
- Flyway migration creation
- Database schema design
- PostgreSQL optimization
- Foreign key relationships
- Index creation
- Data migration strategies

**Example:**
```
@migration-assistant add an 'status' column to the users table
```

## 🪝 Hooks

Hooks automatically execute actions in response to events, providing workflow automation.

### Configured Hooks

#### 1. `suggest-tests` (PostToolUse)
**Triggers:** After creating a Controller or Service
**Action:** Displays reminder to create tests

```
💡 Reminder: Consider creating corresponding tests for this file.
Use @test-generator to create comprehensive tests.
```

#### 2. `build-after-changes` (PostToolUse)
**Triggers:** After multiple source file changes
**Action:** Runs `./gradlew build`

Ensures all changes compile and tests pass.

#### 3. `test-file-changes` (PostToolUse)
**Triggers:** After editing main source files
**Action:** Runs `./gradlew test --rerun-tasks`

Verifies that changes don't break existing tests.

#### 4. `validate-migration` (PostToolUse)
**Triggers:** After creating migration files
**Action:** Validates migration files and shows next steps

```
🔍 Migration files created. Run: ./gradlew flywayMigrate
```

#### 5. `remind-gradle-commands` (PostToolUse)
**Triggers:** After project modifications
**Action:** Displays available Gradle commands

## 🔄 Common Workflows

### Creating a New Endpoint

```
1. Request: "Create a /users endpoint with CRUD operations"
2. Claude delegates to @endpoint-builder
3. endpoint-builder creates:
   - User model/entity
   - UserRepository
   - UserService
   - UserController
   - Request/Response DTOs
   - Database migration
   - Integration tests
   - Unit tests
4. Hook suggests test review
5. Hook runs build verification
```

### Adding a Database Column

```
1. Request: "Add 'phone' column to users table"
2. Claude delegates to @migration-assistant
3. migration-assistant creates:
   - Flyway migration file (V#__add_phone_to_users.sql)
   - Proper constraints and indexes
4. Hook validates migration
5. Provide instructions to run: ./gradlew flywayMigrate
```

### Code Review

```
1. Request: "@spring-reviewer review the UserService"
2. spring-reviewer analyzes code for:
   - Spring patterns
   - Kotlin idioms
   - Error handling
   - Best practices
3. Provides detailed feedback with suggestions
4. Offers corrected code examples
```

### Writing Tests

```
1. Request: "@test-generator create tests for QuoteController"
2. test-generator creates:
   - Integration tests for endpoints
   - Unit tests for service logic
   - Mock setups
   - Edge case coverage
3. Tests follow Kotest "Should Spec" style
```

## 📋 Quick Reference

### Use Skills When:
- You want Claude to follow established patterns automatically
- You're creating code and need guidance on best practices
- You want consistency across the codebase

### Use Sub-Agents When:
- You need specialized expertise for a complex task
- You want focused context on a specific domain
- You want detailed assistance with step-by-step guidance

### Use Hooks When:
- You want automated quality checks
- You want reminders to follow project conventions
- You want automatic test execution

## 🛠️ Configuration

### settings.json

Controls permissions and configuration:

```json
{
  "permissions": {
    "allowedTools": ["Bash(*)", "Edit(*)", "Glob(*)", "Grep(*)", "Read(*)", "Write(*)", "Task(*)"]
  },
  "agents": {
    "spring-reviewer": {...},
    "test-generator": {...},
    "endpoint-builder": {...},
    "migration-assistant": {...}
  },
  "skills": {
    "spring-boot-kotlin-patterns": {...},
    "kotest-test-patterns": {...},
    "flyway-migration-patterns": {...},
    "rest-api-standards": {...}
  },
  "hooks": {
    "enabled": true,
    "continueOnFailure": true
  }
}
```

### hooks.json

Defines hook triggers and actions. Each hook can be enabled/disabled individually.

## 📖 Examples

### Example 1: Create New Feature
```
User: "Add a new /quotes endpoint with GET for random quote"
Claude: Delegates to @endpoint-builder
Result: Complete feature with tests and database setup
```

### Example 2: Code Review Session
```
User: "Review my GreetingController"
Claude: Invokes @spring-reviewer
Result: Detailed feedback on patterns, practices, and improvements
```

### Example 3: Test Generation
```
User: "@test-generator write tests for the DadJokeService"
Claude: Invokes test-generator agent
Result: Comprehensive unit and integration tests in Kotest style
```

### Example 4: Database Changes
```
User: "Add a 'rating' column to the jokes table"
Claude: Delegates to @migration-assistant
Result: Flyway migration file with proper constraints
```

## 📚 Directory Structure

```
.claude/
├── README.md                          # This file
├── settings.json                      # Configuration
├── hooks/
│   └── hooks.json                     # Hook definitions
├── skills/
│   ├── spring-boot-kotlin-patterns/
│   │   └── SKILL.md
│   ├── kotest-test-patterns/
│   │   └── SKILL.md
│   ├── flyway-migration-patterns/
│   │   └── SKILL.md
│   └── rest-api-standards/
│       └── SKILL.md
└── agents/
    ├── spring-reviewer.md
    ├── test-generator.md
    ├── endpoint-builder.md
    └── migration-assistant.md
```

## 🚀 Getting Started

1. **Skills are automatic** - Claude reads them when relevant
2. **Invoke agents** - Use `@agent-name` or descriptive requests
3. **Hooks run automatically** - Monitor console for hook outputs
4. **Review documentation** - Check individual skill/agent files for details

## ✨ Tips

- **Be specific** - Describe what you want clearly
- **Use @mentions** - Explicitly invoke agents: `@endpoint-builder`
- **Check hooks** - Monitor build output from automated hooks
- **Review skills** - Read skill files to understand what's available
- **Ask for help** - Agents can explain patterns and best practices

## 🔗 Related Files

- `CLAUDE.md` - Project-wide development guidelines
- `README.md` - Project overview
- `build.gradle.kts` - Build configuration
- `src/test/kotlin/` - Test examples

## 📝 Notes

- All skills reference the established patterns in this project
- Sub-agents are optimized for the Spring Boot + Kotlin + Kotest stack
- Hooks provide safety checks and reminders
- Customize hooks in `hooks.json` as needed
- All extensions are located in `.claude/` for easy access

---

**Last Updated:** 2024
**Project:** Spring Demo - Spring Boot + Kotlin REST API
**Framework Versions:** Spring Boot 3.5.4, Kotlin 2.2.0, Java 21
