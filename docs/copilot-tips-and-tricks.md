# GitHub Copilot Tips & Tricks

## Table of Contents
1. [Understanding LLMs](#understanding-llms)
2. [Core Concepts](#core-concepts)
3. [General Usage Guidelines](#general-usage-guidelines)
4. [Advanced Features](#advanced-features)
5. [Prompting Strategies](#prompting-strategies)
6. [Language & Framework Specific Tips](#language--framework-specific-tips)
7. [IDE Support & Compatibility](#ide-support--compatibility)
8. [Privacy & Security](#privacy--security)
9. [Troubleshooting](#troubleshooting)

## Understanding LLMs

### Context

Surrounding information that makes an LLM understand what you are talking about.
The more context you have, the higher the quality of the conversation.

### Tokens

In the world of LLMs text is broken down into units of tokens.
A unit can be a word, a part of a word, or even a single letter.
Too few tokens and the LLM may lack context, too many and the LLM can be overwhelmed.

### Limitations

LLMs rely on patterns and probabilities they were trained on.
Models are never perfect. They sometimes provide incorrect answers, also called *hallucinations*.

### Prompts

You can work with LLMs' power and limitations by combining everything into a prompt. A prompt gives a model context via tokens.

**Prompt Engineering** = term for crafting prompts.

**Guidelines:**

* Use tokens to provide context
* Keep prompts clear, concise, and precise
* Break prompts into smaller chunks
* Be specific about your requirements

## Core Concepts

### Code Completion vs. Chat

**Code Completion (Ghost Text)**
- Appears automatically as you type
- Provides inline suggestions
- Best for: Single lines, function completions, boilerplate code
- Triggered by: Context and typing patterns

**Chat Interface**
- Interactive conversation with Copilot
- Handles complex requests and explanations
- Best for: Architecture decisions, debugging, learning
- Triggered by: Explicit prompts and questions

**Example:**
```javascript
// Ghost text suggests as you type:
function calculateTotal(items) {
  // Copilot suggests: return items.reduce((sum, item) => sum + item.price, 0);
}

// Chat is better for:
// "Explain the performance implications of different array methods in JavaScript"
```

## General Usage Guidelines

### Don't Let Copilot Fly the Plane

You are the real pilot. Copilot is meant to be an assistant.
Let it generate code suggestions, not the complete code base!

### Use Copilot's Tools Effectively

Don't use code comments as prompts!

**Inline Chat (Cmd+I / Ctrl+I)**
- Quick edits and modifications
- Context-aware suggestions
- Best for refactoring specific code blocks

**Ghost Text**
- Automatic code completion
- Accepts with Tab, rejects with Esc
- Best for writing new code

**Chat Window**
- Complex questions and explanations
- Multi-turn conversations
- Best for architecture and learning

### Use Slash Commands

Don't recreate existing prompts. Use predefined slash commands.

* `/explain` - Explains selected code or functionality
* `/fix` - Identifies and suggests fixes for bugs or issues
* `/doc` - Generates documentation for your code
* `/tests` - Creates unit tests for your functions or classes
* `/help` - Shows available commands and their usage
* `/new` - Creates new files or components
* `/commit` - Generates commit messages based on changes
* `/review` - Performs code review and suggests improvements

**Example Usage:**
```
/explain #selection
/tests for the calculateTotal function
/doc for this React component
```

### Context is Everything

**Context References:**
* `@workspace` - References your entire workspace/solution
* `#file` - References a specific file in your project
* `#editor` - References the currently open file in the editor
* `#selection` - References currently selected code
* `#terminalSelection` - References selected text from terminal output

**Example:**
```
Refactor #selection to use TypeScript interfaces based on the patterns in #UserService.ts
```

### The 3S Principle

3S = Simple, Specific, Short.

#### Simple

The more code Copilot writes, the higher the chance for hallucinations!
Break your solution into simple steps.

**❌ Complex Request:**
```
Create a complete e-commerce website with user authentication, shopping cart, payment processing, inventory management, and admin dashboard
```

**✅ Simple Steps:**
```
1. Create a user registration form with email validation
2. Add password hashing and storage
3. Implement login functionality
4. Create shopping cart data structure
```

#### Specific

Be precise about requirements, constraints, and expected outcomes.

**❌ Vague:**
```
Make this code better
```

**✅ Specific:**
```
Optimize this Python function for better performance, add type hints, and include error handling for edge cases
```

#### Short

Keep prompts concise but informative. Aim for 1-3 sentences.

**❌ Too Long:**
```
I have this function that processes user data and I need it to be faster because right now it's really slow when we have lots of users and it's causing problems in production and I think maybe we could use caching or something but I'm not sure what would be best for our specific use case since we're using Redis already but maybe we need a different approach...
```

**✅ Concise:**
```
Optimize this user data processing function using Redis caching to improve performance for high user loads
```

## Advanced Features

### Custom Instructions (`.github/copilot-instructions.md`)

**Purpose:** Repository-wide instructions automatically added to each chat request

**Best Practices:**
- Keep instructions concise and specific
- Focus on coding standards and conventions
- Include framework and tool preferences
- Specify testing requirements

**Example for Spring Boot + Kotlin Teams:**
```markdown
Always use Kotlin with Spring Boot for backend development.
Prefer @Controller over @RestController for our REST endpoints.
Use JdbcTemplate for database operations, not JPA/Hibernate.
All database migrations must be Flyway scripts following V{number}__{description}.sql format.
Write tests using Kotest with property-based testing where applicable.
Use Gradle with Kotlin DSL (build.gradle.kts) for build scripts.
Follow our code review checklist in docs/code-review-standards.md.
When generating database code, use UUID for primary keys and snake_case for table names.
```

### Shared Prompts (`.github/prompts/`)

**Purpose:** Reusable templates for specific tasks

**File Structure:**
```
.github/prompts/
├── code-review.prompt.md
├── api-design.prompt.md
├── security-audit.prompt.md
└── documentation.prompt.md
```

**Example Prompt File for Spring Boot + Kotlin:**
```markdown
---
title: "Spring Boot Service Layer Generator"
description: "Creates service layer with JDBC repositories for our Kotlin stack"
---

# Spring Boot Service Layer

Generate a complete service layer for {{entity_name}} following our patterns:

## Requirements:
- Use Kotlin with Spring Boot
- Create @Service class with constructor injection
- Generate @Repository with JdbcTemplate operations
- Include proper exception handling
- Add transaction management with @Transactional

## Database Context:
- Table name: {{table_name}} (snake_case)
- Primary key: UUID type
- Use JdbcTemplate for all database operations

## Testing:
- Generate Kotest FunSpec tests
- Include property-based testing for validation
- Mock dependencies using MockK

## Variables:
- Entity: {{entity_name}}
- Table: {{table_name}}
- Operations: {{crud_operations}}
```

Shared prompts must use the `.prompt.md` extension, not just `.md`. This distinguishes them from regular markdown files and enables VS Code to recognize them as Copilot prompt templates that can be invoked with slash commands like `/code-review`.

### Main Differences

| Aspect | copilot-instructions.md | Shared Prompts |
|--------|------------------------|----------------|
| **Activation** | Automatically with every chat request | Manually via `#prompt-name` |
| **Scope** | Repository-wide | Task-specific |
| **Number of files** | Single file | Multiple files possible |
| **Purpose** | Coding standards, conventions | Workflows, templates, specific tasks |
| **Variables** | No | Yes ({{variable_name}}) |
| **Customization** | Global preferences | Situational instructions |

### Agent Mode

Three options: Ask, Edit, Agent

* **Ask**: Provide answers in Chat without making changes to your files
* **Edit**: Same answer but Copilot creates and modifies files on their own
* **Agent**: Acts more autonomously, planning and executing multi-step tasks

**When to use each:**
- **Ask**: Learning, exploring options, getting explanations
- **Edit**: Quick fixes, refactoring, adding features to existing files
- **Agent**: Creating new projects, complex multi-file changes

### MCP (Model Context Protocol)

MCP enables Copilot to connect to external data sources and tools.

**Key Benefits:**
- Real-time data access from APIs and databases
- Integration with custom business systems
- Extended tool capabilities
- Standardized protocol for consistent integration

**Setting up MCP:**
1. Install MCP server for your service
2. Configure authentication
3. Add to Copilot settings
4. Test with simple queries

**Discovery Resources:**
- https://mcp.so/
- GitHub repositories tagged "mcp"
- Vendor-specific implementations

## Prompting Strategies

### Q&A Prompt

Let Copilot ask you questions to gather context.

**Example:**
```
I want to create a user authentication system. Ask me questions to understand my requirements before generating code.
```

**Copilot might ask:**
- What authentication method do you prefer? (JWT, sessions, OAuth)
- What database are you using?
- Do you need password reset functionality?
- Are there specific security requirements?

### Pros and Cons Prompt

Get comparative analysis before implementation.

**Example:**
```
Compare JWT vs. session-based authentication for a Node.js API. Include pros, cons, and recommend the best approach for a mobile app backend.
```

### Stepwise Chain of Thought

Break complex tasks into logical steps.

**Example:**
```
Help me implement user registration step by step:
1. Design the database schema
2. Create the API endpoint
3. Add input validation
4. Implement password hashing
5. Add error handling
6. Write tests
```

### Role-Based Prompting

Leverage specialized expertise perspectives.

**Examples:**
```
Act as a senior security engineer and review this authentication code for vulnerabilities.

As a performance optimization expert, analyze this database query and suggest improvements.

From a DevOps perspective, what monitoring should I add to this microservice?
```

### Negative Prompting

Tell Copilot what NOT to do.

**Example:**
```
Create a React component for user profiles. 
DO NOT use class components, DO NOT include inline styles, 
DO NOT fetch data directly in the component.
```

## Language & Framework Specific Tips

### JavaScript/TypeScript

**Best Practices:**
```
// Good context for Copilot
interface User {
  id: string;
  email: string;
  profile: UserProfile;
}

// Ask for specific patterns
"Create a React hook for managing user state with TypeScript"
"Generate Express middleware with proper error handling"
```

### Kotlin & Spring Boot

**Framework-Specific Context:**
```kotlin
// Good context for Copilot - Spring Boot with Kotlin
@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val jdbcTemplate: JdbcTemplate
) {

// Effective prompts for our stack
"Create a Spring Boot service layer using JDBC and @Repository pattern"
"Generate Kotest property-based tests for this data class"
"Create Flyway migration script for this table structure"
"Generate @Controller with JDBC operations for user management"
```

**Best Practices for Kotlin Development:**
```kotlin
// Provide data class context
data class User(
    val id: UUID,
    val email: String,
    val createdAt: Instant
)

// Effective prompts
"Create a JDBC repository for this User data class using JdbcTemplate"
"Generate Kotest tests with property-based testing for email validation"
"Create a Gradle build script task for running database migrations"
```

**Spring Boot + JDBC Specific Prompts:**
```
"Create a @Repository class using JdbcTemplate for CRUD operations on User table"
"Generate a @Service layer that handles user business logic with transaction management"
"Create Flyway migration V001__Create_users_table.sql for the User data class"
"Write Kotest specs with property-based testing for user service validation"
```

### Kotest & Property-Based Testing

**Effective Test Generation:**
```kotlin
// Provide test context
class UserServiceTest : FunSpec({
    test("should validate email format") {
        checkAll(Arb.email()) { email ->
            // Property-based test logic
        }
    }
})

// Specific prompts for our testing approach
"Generate Kotest FunSpec tests with property-based testing for user validation"
"Create MockK mocks for JDBC dependencies in Kotest"
"Write Kotest tests that verify Flyway migration behavior"
"Generate property-based tests using Arb generators for data validation"
```

**Gradle + Kotlin DSL:**
```kotlin
// Build script context
plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    id("org.springframework.boot") version "3.2.0"
}

// Effective prompts
"Add Kotest dependencies to build.gradle.kts"
"Create Gradle task for running Flyway migrations"
"Configure test containers in Gradle Kotlin DSL"
```

## IDE Support & Compatibility

### Feature Support by IDE

Different IDEs have varying levels of support for GitHub Copilot's advanced features. Here's a comprehensive breakdown:

| Feature | VS Code | IntelliJ IDEA | Visual Studio | Other IDEs |
|---------|---------|---------------|---------------|------------|
| **Code Completion** | ✅ Full | ✅ Full | ✅ Full | ✅ Most |
| **Chat Interface** | ✅ Full | ✅ Full | ✅ Full | ⚠️ Limited |
| **Custom Instructions** | ✅ Full | ✅ Supported | ✅ Supported | ❌ Not available |
| **Shared Prompts (.prompt.md)** | ✅ Full | ❌ Not supported | ❌ Not supported | ❌ Not supported |
| **Agent Mode** | ✅ Full (Preview) | ❌ Not available | ❌ Not available | ❌ Not available |
| **Slash Commands** | ✅ Full | ✅ Basic | ✅ Full | ⚠️ Limited |
| **Chat Participants** | ✅ @workspace | ✅ @project | ✅ @workspace | ❌ Varies |
| **File References** | ✅ #file syntax | ✅ Manual attach | ✅ #file syntax | ⚠️ Limited |
| **MCP Integration** | ✅ Full | ❌ Not available | ❌ Not available | ❌ Not available |

### VS Code (Most Feature-Complete)

**Unique Features:**
- Shared prompts with `.prompt.md` files
- Agent mode (preview)
- Full MCP server integration
- Advanced chat participants (@workspace, @vscode)
- Comprehensive slash command library

**Best For:**
- Teams wanting to use shared prompt templates
- Advanced AI-assisted development workflows
- Experimental feature access

### IntelliJ IDEA & JetBrains IDEs

**Supported Features:**
- Basic code completion and chat
- Custom instructions (`.github/copilot-instructions.md`)
- @project chat participant (equivalent to @workspace)
- Manual file attachment to chat

**Limitations:**
- No shared prompts (`.prompt.md` files)
- No agent mode
- Limited slash command support
- No MCP integration

**Workarounds for IntelliJ Users:**

1. **Replace Shared Prompts:**
   ```markdown
   # Create a personal prompts folder
   .idea/prompts/
   ├── code-review-checklist.md
   ├── api-security-audit.md
   └── testing-guidelines.md
   
   # Copy-paste content as needed
   ```

2. **Use Custom Instructions Effectively:**
   ```markdown
   # .github/copilot-instructions.md
   When reviewing code, always check for:
   - Security vulnerabilities
   - Performance implications
   - Test coverage
   - Documentation completeness
   
   Our tech stack: Spring Boot, React, PostgreSQL
   Always use our established patterns in the /patterns directory.
   ```

3. **Manual Context Management:**
   - Keep relevant files open
   - Use @project for workspace context
   - Drag-and-drop files into chat
   - Reference specific classes/methods in prompts

**Example IntelliJ Workflow (Current Limitations):**
```
# Instead of using shared prompts in VS Code:
@project Create a Spring Boot service layer for User entity using our patterns:
- Use @Service with constructor injection
- Create @Repository with JdbcTemplate
- Include Kotest tests with property-based testing
- Follow our database naming conventions in docs/database-standards.md
- Generate Flyway migration script

# Manual context: Attach UserController.kt and existing service examples
```

**When to Consider VS Code for AI Tasks:**

**Scenario 1: Large-Scale Refactoring**
- Use VS Code agent mode for complex multi-file refactoring
- Leverage project-wide context understanding
- Return to IntelliJ for implementation and debugging

**Scenario 2: Documentation Generation**
- Use shared prompts for consistent documentation
- Generate API documentation with AI assistance
- Create team onboarding materials

**Scenario 3: Architecture Planning**
- Use agent mode for system design discussions
- Generate architectural decision records (ADRs)
- Plan database schema changes with AI assistance

**Setup for Dual IDE Workflow:**
```bash
# VS Code extensions for Kotlin development
- Kotlin Language
- Spring Boot Extension Pack
- GitHub Copilot + Copilot Chat
- Gradle for Java

# Sync settings between IDEs
- Share .editorconfig
- Use same code formatting rules
- Consistent Git hooks and pre-commit checks
```

### Visual Studio

**Supported Features:**
- Full code completion and chat
- Custom instructions support
- @workspace chat participant
- File reference syntax (#file)
- Comprehensive slash commands

**Limitations:**
- No shared prompts
- No agent mode
- Limited MCP integration

### Other IDEs (Vim, Neovim, etc.)

**Basic Support:**
- Code completion (varies by implementation)
- Limited or no chat interface
- No advanced features

**Recommendations:**
- Use vim-copilot or similar plugins for basic functionality
- Consider using GitHub Copilot CLI for chat features
- Supplement with web-based Copilot Chat

### Migration Strategies

**From VS Code to IntelliJ:**
1. Convert `.prompt.md` files to documentation
2. Consolidate common patterns into custom instructions
3. Create personal prompt library in `.idea/` folder
4. Train team on manual context management

**From IntelliJ to VS Code:**
1. Install Copilot Chat extension
2. Create `.github/prompts/` folder
3. Convert personal prompts to `.prompt.md` format
4. Set up MCP servers if needed

### Future Compatibility

**Expected Developments:**
- Shared prompts may come to other IDEs
- Agent mode expansion to IntelliJ/Visual Studio
- Improved MCP support across platforms
- Standardization of chat participants

**Current Recommendation for Spring Boot + Kotlin Teams:**
- **IntelliJ**: Primary IDE for daily development and debugging
- **VS Code**: Secondary tool for AI-intensive tasks and team collaboration
- **Hybrid Approach**: Switch tools based on specific task requirements
- **Future**: Monitor IntelliJ plugin development for advanced AI features

## Privacy & Security

### Data Handling

**What Copilot Sees:**
- Code you're actively working on
- Comments and documentation
- File names and structure
- Git commit messages (in some contexts)

**What Copilot Doesn't Store:**
- Individual user code (with proper settings)
- Proprietary business logic
- Sensitive data (when properly configured)

### Security Best Practices

**For Organizations:**
1. Enable content exclusion filters
2. Configure IP allow lists
3. Use audit logs
4. Implement code scanning
5. Train developers on secure prompting

**For Developers:**
1. Avoid putting secrets in code
2. Review generated code for vulnerabilities
3. Use descriptive but non-sensitive variable names
4. Be cautious with database schemas in prompts

### Enterprise Controls

**Available Security Features:**
- Content exclusion (exclude sensitive repositories)
- Audit logging and monitoring
- IP restrictions
- SSO integration
- Policy enforcement

## Troubleshooting

### Common Issues and Solutions

#### Poor Code Suggestions

**Problem:** Copilot generates irrelevant or incorrect code

**Solutions:**
- Add more specific context
- Break down complex requests
- Use explicit typing and interfaces
- Provide examples of desired patterns

**Before:**
```
"Make this function better"
```

**After:**
```
"Optimize this function for performance, add TypeScript types, and include error handling for null inputs"
```

#### Inconsistent Code Style

**Problem:** Generated code doesn't match project conventions

**Solutions:**
- Use `.github/copilot-instructions.md`
- Provide style examples in prompts
- Reference existing code files
- Configure IDE formatting rules

#### Slow Response Times

**Problem:** Copilot takes too long to respond

**Solutions:**
- Reduce context size
- Use more specific prompts
- Check network connectivity
- Clear IDE caches
- Restart Copilot service

#### Hallucinated APIs or Libraries

**Problem:** Copilot suggests non-existent functions or libraries

**Solutions:**
- Verify library versions in prompts
- Reference official documentation
- Use `#fetch` for up-to-date information
- Cross-reference generated code

### Debugging Prompts

**Ineffective Prompt Analysis:**
1. Too vague or too specific?
2. Missing context?
3. Conflicting requirements?
4. Outdated assumptions?

**Prompt Refinement Process:**
1. Start broad, then narrow down
2. Add examples of desired output
3. Specify constraints clearly
4. Test with simple cases first

---

## Contributing to This Guide

This guide is a living document. To contribute:
1. Share successful prompting patterns
2. Report issues and solutions
3. Add language-specific tips
4. Suggest new sections

Remember: The key to effective Copilot usage is treating it as a collaborative partner, not a replacement for your expertise and judgment.