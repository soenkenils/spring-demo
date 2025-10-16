# Flyway Migration Patterns

## Description

Database migration best practices using Flyway for PostgreSQL in this Spring Boot project.

**Use this skill when:**
- Creating new database migrations
- Modifying database schema
- Adding new tables or columns
- Planning database changes

## Naming Convention

Migration files follow the Flyway naming convention:

```
V{version}__{description}.sql
```

**Rules:**
- `V` prefix is required
- Version numbers are sequential (V1, V2, V3, etc.)
- Double underscore `__` separates version from description
- Description uses snake_case
- Only uppercase letters, numbers, and underscores

**Examples:**
```
V1__create_dad_jokes_table.sql
V2__add_author_to_dad_jokes.sql
V3__create_users_table.sql
V4__add_email_unique_constraint.sql
```

## Location

Place all migrations in:
```
src/main/resources/db/migration/
```

## Basic Structure

Every migration should:
1. Start with transaction control if needed
2. Use explicit schema and table names
3. Be idempotent where possible (can be run multiple times safely)
4. Include comments explaining purpose
5. Follow PostgreSQL conventions

## Examples

### Creating a Table

```sql
-- V1__create_dad_jokes_table.sql
CREATE TABLE dad_jokes (
    id BIGSERIAL PRIMARY KEY,
    joke_text VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_dad_jokes_created_at ON dad_jokes(created_at DESC);
```

### Adding a Column

```sql
-- V2__add_author_to_dad_jokes.sql
ALTER TABLE dad_jokes
ADD COLUMN author VARCHAR(255);

COMMENT ON COLUMN dad_jokes.author IS 'Author or source of the joke';
```

### Adding a Constraint

```sql
-- V3__add_email_unique_constraint_to_users.sql
ALTER TABLE users
ADD CONSTRAINT uk_users_email UNIQUE (email);
```

### Creating an Index

```sql
-- V4__create_index_on_users_created_at.sql
CREATE INDEX idx_users_created_at ON users(created_at DESC);
```

### Data Modifications

```sql
-- V5__populate_default_jokes.sql
INSERT INTO dad_jokes (joke_text, author)
VALUES
    ('Why don''t scientists trust atoms? Because they make up everything!', 'Unknown'),
    ('I told my computer I needed a break. Now it won''t stop sending me Kit-Kat ads.', 'Unknown');
```

### Adding a Foreign Key

```sql
-- V6__add_foreign_key_jokes_to_users.sql
ALTER TABLE dad_jokes
ADD COLUMN user_id BIGINT,
ADD CONSTRAINT fk_dad_jokes_user_id
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
```

## PostgreSQL Best Practices

### 1. Use Sequences for IDs

```sql
CREATE TABLE jokes (
    id BIGSERIAL PRIMARY KEY,  -- Equivalent to: id BIGINT DEFAULT nextval('jokes_id_seq')
    joke_text VARCHAR(500) NOT NULL
);
```

### 2. Add Timestamps

```sql
CREATE TABLE jokes (
    id BIGSERIAL PRIMARY KEY,
    joke_text VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Use Constraints

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    age INT CHECK (age >= 0),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE'
);
```

### 4. Use Meaningful Column Names

```sql
-- Good
CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    published_at TIMESTAMP
);

-- Avoid single-letter or unclear names
```

### 5. Add Comments

```sql
CREATE TABLE dad_jokes (
    id BIGSERIAL PRIMARY KEY,
    joke_text VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE dad_jokes IS 'Stores dad jokes for the application';
COMMENT ON COLUMN dad_jokes.joke_text IS 'The text of the joke';
COMMENT ON COLUMN dad_jokes.created_at IS 'When the joke was added to the database';
```

### 6. Use Transactions Carefully

Most DDL statements are auto-committed in PostgreSQL, so explicit transactions are usually not needed:

```sql
-- Not necessary, but allowed
BEGIN;
    CREATE TABLE users (id BIGSERIAL PRIMARY KEY);
    CREATE TABLE posts (id BIGSERIAL PRIMARY KEY);
COMMIT;
```

## Idempotent Migrations

Try to write migrations that can be run multiple times safely:

```sql
-- Idempotent: Only creates if not exists
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Idempotent: Only adds column if it doesn't exist
ALTER TABLE users
ADD COLUMN IF NOT EXISTS age INT;

-- NOT idempotent: Will fail if column already exists
ALTER TABLE users ADD COLUMN phone VARCHAR(20);
```

## Never Do This

1. **Never modify existing migration files** - Flyway tracks checksums
2. **Never drop tables in production** - Archive data first
3. **Never use WITH RECURSIVE without limits** - Can cause infinite loops
4. **Never use DDL in transactions without understanding implications**
5. **Never migrate without testing on a copy of production data**

## Running Migrations

```bash
# Run migrations
./gradlew flywayMigrate

# Check migration status
./gradlew flywayInfo

# Clean database (DANGEROUS - only in dev!)
./gradlew flywayClean
```

## Testing Migrations

Create a test configuration in `src/test/resources/application-test.properties` to use TestContainers:

```kotlin
@SpringBootTest
@Import(TestContainerConfig::class)
@ActiveProfiles("test")
class MigrationTest : ShouldSpec({
    context("Migrations") {
        should("be applied successfully") {
            // Testcontainers automatically runs migrations
            // Verify tables exist
        }
    }
})
```

## Performance Considerations

### Indexes

Create indexes on frequently queried columns:

```sql
-- Good for filtering and sorting
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_posts_created_at ON posts(created_at DESC);
CREATE INDEX idx_posts_author_id_created_at ON posts(author_id, created_at DESC);
```

### Large Tables

For large data migrations, batch the operations:

```sql
-- For millions of rows, use batching in application code
-- Don't put it all in one SQL statement
INSERT INTO archive_users
SELECT * FROM users WHERE created_at < CURRENT_DATE - INTERVAL '1 year'
LIMIT 10000;
```

## Naming Convention Examples

| File | Purpose |
|------|---------|
| V1__create_initial_schema.sql | Initial database setup |
| V2__create_dad_jokes_table.sql | New table for dad jokes |
| V3__add_user_preferences_column.sql | Add column to existing table |
| V4__create_idx_user_email.sql | Add performance index |
| V5__add_fk_jokes_users.sql | Add foreign key relationship |
| V6__populate_default_data.sql | Insert initial data |
| V7__rename_column_joke_text_to_content.sql | Schema refactor |
| V8__create_audit_log_table.sql | Add auditing |

## Rollback Strategy

Flyway doesn't support automatic rollbacks, so:

1. **Test migrations thoroughly** before production
2. **Keep migration scripts simple** for easier debugging
3. **Document rollback procedures** for critical migrations
4. **Create undo migrations** for destructive changes (optional)

Example of paired migrations:

```sql
-- V1__create_users.sql
CREATE TABLE users (id BIGSERIAL PRIMARY KEY);

-- V2__add_admin_role.sql
CREATE TABLE admin_roles (id BIGSERIAL PRIMARY KEY);

-- V3__drop_admin_roles.sql (if needed later)
DROP TABLE admin_roles;
```
