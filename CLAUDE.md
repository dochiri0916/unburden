# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
./gradlew build          # Build and test
./gradlew bootRun        # Run the application
./gradlew test           # Run all tests (JUnit 5)
./gradlew clean build    # Clean rebuild
```

Requires Java 25. Uses Gradle wrapper (no global Gradle install needed).

## Tech Stack

- **Java 25 / Spring Boot 4.0.2**
- Spring Data JPA + H2 (in-memory, MySQL mode)
- Spring Security + JWT (jjwt 0.12.6)
- Spring AI + Ollama (mistral:7b-instruct)
- RabbitMQ for event publishing
- QueryDSL 5.1.0 for type-safe queries
- Lombok for boilerplate reduction

## Architecture

Layered architecture under `src/main/java/com/unburden/`:

- **`presentation/`** — REST controllers with request/response DTOs. Global exception handler translates domain exceptions to RFC 7807 `ProblemDetail` responses via `DomainExceptionStatusMapper` strategy pattern.
- **`application/`** — Business logic services with CQRS-style separation (`command/` vs `query/`). Facade pattern used for multi-step workflows (`LoginFacade`, `ReissueTokenFacade`). Publishes domain events (e.g., `JournalWrittenEvent` to RabbitMQ).
- **`domain/`** — Entities, value objects, domain exceptions. `BaseEntity` provides JPA auditing (`createdAt`, `updatedAt`, `createdBy`, `updatedBy`) and soft delete (`deletedAt`). Each domain has its own abstract exception base class with concrete subtypes.
- **`infrastructure/`** — JPA repositories, Spring Security config, JWT components, RabbitMQ messaging, AI client config, scheduled tasks, and `@ConfigurationProperties` records.

## Domain Model

- **User** — email (unique), password (BCrypt), name, role (USER/ADMIN), soft-deletable
- **Journal** — user's daily journal entry (one per user per day limit), status: WRITTEN → PROCESSED
- **Letter** — AI-generated empathetic response to a journal, status: CREATED → SENT → READ
- **RefreshToken** — long-lived token stored in DB for revocation, auto-cleaned daily at 3 AM

## Key Conventions

- Java records used extensively for DTOs, configuration properties, and value objects
- Exception messages are in Korean (user-facing)
- Commit messages are in Korean
- API endpoints prefixed with `/api/`
- Refresh tokens delivered via HttpOnly/Secure cookies
- `AuditorAwareImpl` resolves current user from JWT or defaults to "SYSTEM"
- Spring AI prompts written in Korean with strict JSON output formatting
