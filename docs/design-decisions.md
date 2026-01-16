# Booking Engine - Architectural Design Decisions

## Overview

This document captures the key architectural decisions made in the Booking Engine project. These decisions follow principles from Robert C. Martin's "Clean Architecture" book, prioritizing domain understanding and flexibility over premature technical commitments.

---

## ADR-001: Domain-First Development with In-Memory Repositories

**Status**: Active

**Context**:
Early in the project, we needed to decide whether to implement a database layer immediately or focus on understanding and modeling the domain first.

**Decision**:
Implement in-memory repositories with manual locking mechanisms instead of committing to a specific database technology.

**Rationale**:
Following Clean Architecture principles (Chapter 22: "The Clean Architecture"), we delay infrastructure decisions to avoid costly refactoring when domain understanding evolves. As Uncle Bob states:

> "A good architecture allows major decisions to be deferred... The longer you wait to make those decisions, the more information you have to make them properly."

**Benefits**:
- **Rapid domain iteration**: Changes to domain models don't require database migrations
- **Fast test execution**: No database setup/teardown overhead in tests
- **Technology independence**: Domain logic remains decoupled from persistence concerns
- **Clear boundaries**: Forces explicit thinking about repository contracts

**Implementation Details**:
- `LockingTemplate` utility provides thread-safe operations
- Defensive copying in repositories simulates JPA detached entity behavior
- Manual locking prepares team for eventual transaction management concepts

**Trade-offs**:
- In-memory storage doesn't persist across restarts (acceptable for development phase)
- Manual lock management requires discipline (mitigated by utility class)
- No query optimization insights yet (deferred until domain stabilizes)

**References**:
- Clean Architecture, Chapter 22: "The Clean Architecture"
- Clean Architecture, Chapter 34: "The Missing Chapter"

---

## ADR-002: Use Case Layer as Application Boundary

**Status**: Active

**Context**:
The system needs to support different deployment models (monolith now, potential microservices later) without major rewrites.

**Decision**:
Introduce a Use Case layer between controllers and domain components, even when use cases appear to be simple delegations.

**Rationale**:
Following the Dependency Rule from Clean Architecture (Chapter 22), use cases represent application-specific business rules and provide ports in hexagonal architecture. As Uncle Bob explains:

> "Use cases orchestrate the flow of data to and from the entities, and direct those entities to use their enterprise-wide business rules to achieve the goals of the use case."

**Benefits**:
- **Deployment flexibility**: Current monolith can be decomposed into microservices by swapping implementations
- **Clear application boundaries**: Separates "what the system does" from "how it does it"
- **Testability**: Use cases can be tested independently of delivery mechanisms
- **Interface-based design**: Enables dependency injection and multiple implementations

**Example Scenario**:
```
Current: CreateBookingUseCase -> BookingManagerComponent (in-process)

Future:  CreateBookingUseCase -> BookingManagerComponent (gRPC client)
                               -> SlotManagerComponent (in-process)
```

Controllers remain unchanged; only DI configuration changes.

**Trade-offs**:
- Additional layer may seem like over-engineering initially
- Simple use cases appear to just delegate (this is intentional)
- More classes to maintain (justified by flexibility gained)

**References**:
- Clean Architecture, Chapter 20: "Business Rules"
- Clean Architecture, Chapter 22: "The Clean Architecture"
- Clean Architecture, Chapter 33: "Case Study: Video Sales"

---

## ADR-003: Manual Dependency Injection Configuration

**Status**: Active

**Context**:
Spring Boot offers component scanning and auto-configuration, but this can make dependency graphs unclear in large codebases.

**Decision**:
Use explicit `@Configuration` classes with `@Import` annotations and manual bean definitions instead of relying on `@ComponentScan` and auto-configuration.

**Rationale**:
Explicit configuration makes the dependency graph visible and understandable. This aligns with Clean Architecture's emphasis on explicit dependencies and the Dependency Inversion Principle.

**Benefits**:
- **Clarity**: Easy to see what components are created and their dependencies
- **Compile-time safety**: Missing dependencies fail at startup, not at runtime
- **Refactoring confidence**: IDE can track all usages of components
- **Onboarding**: New developers can trace the object graph easily
- **No magic**: Reduces cognitive load from "where did this come from?"

**Implementation Pattern**:
```java
@Configuration
@Import({
  BookingManagerComponentConfig.class,
  SlotManagerComponentConfig.class,
  // ... explicit list of all configurations
})
public class MainConfig {
}
```

Each component has its own configuration class that explicitly declares its beans.

**Trade-offs**:
- More verbose than auto-configuration
- Requires manual updates when adding new components
- Goes against Spring Boot's "convention over configuration" philosophy

**Justification**:
In codebases with 5+ developers and 10+ components, explicit configuration prevents the "where does this come from?" problem and makes the architecture self-documenting.

**References**:
- Clean Architecture, Chapter 11: "DIP: The Dependency Inversion Principle"
- Clean Architecture, Chapter 17: "Boundaries: Drawing Lines"

---

## ADR-004: Defensive Copying in Repositories

**Status**: Active

**Context**:
In-memory repositories return references to objects. Without defensive copying, callers could mutate repository state directly.

**Decision**:
Implement defensive copying in repository methods to simulate database detached entity behavior.

**Rationale**:
Prepares the codebase for eventual JPA integration where entities returned from repositories are detached copies. This prevents accidental state mutation and enforces explicit save operations.

**Benefits**:
- **Prevents bugs**: Callers cannot accidentally mutate repository state
- **Simulates persistence**: Mimics JPA detached entity semantics
- **Smooth transition**: When adding real database, behavior remains consistent
- **Explicit intent**: Forces explicit `save()` calls to persist changes

**Implementation Example**:
```java
@Override
public Optional<Club> find(long clubId) {
    Club value = store.get(clubId);
    if (value != null) {
        return Optional.of(copyClub(value)); // Defensive copy
    }
    return Optional.empty();
}
```

**Trade-offs**:
- Performance overhead from copying (negligible in development phase)
- More code to maintain copy methods
- Memory overhead from duplicate objects

**Justification**:
The overhead is acceptable during domain modeling phase and teaches the team to think about entity lifecycle, which is crucial for eventual persistence layer.

**References**:
- Effective Java (3rd Edition), Item 50: "Make defensive copies when needed"
- Clean Architecture, Chapter 23: "Presenters and Humble Objects"

---

## ADR-005: Delayed REST Controller Implementation

**Status**: Active

**Context**:
The system needs a delivery mechanism, but the domain model is still evolving.

**Decision**:
Delay REST controller implementation until domain model stabilizes. Focus on domain logic and use cases first.

**Rationale**:
Following Clean Architecture's Dependency Rule (Chapter 22), the domain should not depend on delivery mechanisms. As Uncle Bob states:

> "The web is a delivery mechanism... Your system architecture should be just as usable for a console app, a web app, or even a thick client app."

**Benefits**:
- **Domain focus**: Team concentrates on business rules, not HTTP concerns
- **Flexibility**: Can add REST, GraphQL, gRPC, or CLI without domain changes
- **Avoid premature API design**: API contracts should reflect stable domain, not drive it
- **Faster iteration**: No need to update API specs during domain refactoring

**Current State**:
- Domain components are complete and tested
- Use cases provide application boundary
- Controller module exists but is minimal
- PlantUML diagrams document intended API flows

**Next Steps** (when domain stabilizes):
1. Implement REST controllers using use cases
2. Add OpenAPI/Swagger documentation
3. Implement proper error handling and validation
4. Add integration tests with MockMvc or REST Assured

**Trade-offs**:
- No working API for external testing yet
- Cannot gather user feedback on API design
- Stakeholders cannot see "working software" easily

**Justification**:
For a small team (<5 developers) in early stages, getting the domain right is more valuable than having a premature API that will change frequently.

**References**:
- Clean Architecture, Chapter 22: "The Clean Architecture"
- Clean Architecture, Chapter 21: "Screaming Architecture"

---

## ADR-006: Component-Based Architecture

**Status**: Active

**Context**:
The system has multiple bounded contexts (clubs, bookings, slots, auctions, etc.) that need clear boundaries.

**Decision**:
Organize code into components, each with:
- API package (interfaces, DTOs, exceptions) - public contract
- Implementation package (domain logic) - package-private
- Configuration class for dependency injection

**Rationale**:
This follows Domain-Driven Design's bounded context pattern and Clean Architecture's component principles. Each component is a deployable unit with a clear public interface.

**Structure**:
```
component/
├── booking/
│   ├── api/                    # Public contract
│   │   ├── BookingManagerComponent.java
│   │   ├── BookingView.java
│   │   └── CreateBookingCommand.java
│   ├── Booking.java            # Package-private domain entity
│   ├── BookingManagerComponentImpl.java
│   └── BookingManagerComponentConfig.java
```

**Benefits**:
- **Clear boundaries**: API package defines what's public
- **Encapsulation**: Implementation details are package-private
- **Prevents cross-component coupling**: Package-private visibility prevents developers from accidentally using implementation classes (like repositories) from one component in another, forcing them to use the public interface instead. This is especially valuable for newcomers who might be tempted to take shortcuts by directly accessing another component's repository rather than going through its public API
- **Independent deployment**: Components can be extracted to separate services
- **Parallel development**: Teams can work on different components independently

**Trade-offs**:
- More packages and files
- Requires discipline to maintain boundaries
- Package-private visibility requires careful organization

**References**:
- Clean Architecture, Chapter 12: "Components"
- Clean Architecture, Chapter 13: "Component Cohesion"
- Domain-Driven Design (Eric Evans), Chapter 14: "Maintaining Model Integrity"

---

## ADR-007: Event-Driven Architecture with Message Publisher

**Status**: Active

**Context**:
Components need to react to changes in other components without tight coupling.

**Decision**:
Implement event-driven architecture using a `MessagePublisher` interface that publishes domain events.

**Rationale**:
Events enable loose coupling between components and support eventual consistency. Components publish events about what happened; listeners decide how to react.

**Benefits**:
- **Loose coupling**: Components don't know about their consumers
- **Extensibility**: New listeners can be added without changing publishers
- **Audit trail**: Events provide natural audit log
- **Eventual consistency**: Supports distributed system patterns

**Implementation**:
```java
messagePublisher.publish(BookingCreatedEvent.of(bookingId, userId, slotId));
```

Listeners in controller module react to events:
```java
@Component
class BookingCreatedEventListener implements MessageListener<BookingCreatedEvent> {
    // React to event
}
```

**Future Evolution**:
- Current: In-process event bus
- Future: Message broker (RabbitMQ, Kafka) for distributed events

**Trade-offs**:
- Harder to trace flow than direct method calls
- Eventual consistency requires careful design
- Testing requires event verification

**References**:
- Clean Architecture, Chapter 22: "The Clean Architecture"
- Domain-Driven Design (Eric Evans), Chapter 8: "Domain Events"

---

## ADR-008: State Pattern for Slot Lifecycle

**Status**: Active

**Context**:
Slots have complex state transitions with different behaviors in each state.

**Decision**:
Implement State pattern for slot lifecycle management.

**Rationale**:
State pattern encapsulates state-specific behavior and makes transitions explicit. This is a classic Gang of Four pattern well-suited for lifecycle management.

**States**:
- `CreatedSlotState`: Initial state, can transition to Available or PreReserved
- `AvailableSlotState`: Can be reserved by users
- `PreReservedState`: Temporarily held (auction/class)
- `ReservedState`: Booked by specific user

**Benefits**:
- **Clear transitions**: Each state defines valid transitions
- **Encapsulated behavior**: State-specific logic lives in state classes
- **Type safety**: Invalid transitions are compile-time errors
- **Testability**: Each state can be tested independently

**Trade-offs**:
- More classes than simple enum + switch
- State transitions create new slot instances (immutability)

**References**:
- Design Patterns (Gang of Four), Chapter 5: "State"
- Clean Architecture, Chapter 20: "Business Rules"

---

## ADR-009: Functional Repository Pattern

**Status**: Active

**Context**:
Traditional repository patterns accept entities as parameters for save/update operations, requiring transactions to start before domain logic executes. This couples transaction management to the application layer and limits flexibility for distributed data grid implementations.

**Decision**:
Repository methods accept transformation functions (lambdas/method references) that encapsulate the modification logic, rather than accepting modified entities directly.

**Rationale**:
By passing functions instead of entities, the repository controls when and how the transformation is applied. This enables the transactional boundary to be pushed down to the infrastructure layer and allows the logic to be serialized and executed remotely in distributed systems.

**Implementation Example**:
```java
// Traditional approach (NOT used)
Club club = repository.find(clubId);
club.addJoinRequest(joinRequest);
repository.save(club);  // Transaction must wrap all of this

// Functional approach (USED)
repository.addJoinRequest(clubId, 
    club -> {
        club.addJoinRequest(joinRequest);
        return club;
    }, 
    () -> new ClubNotFoundException(clubId));
```

From `ClubRepositoryInMemory`:
```java
@Override
public void addJoinRequest(long clubId, UnaryOperator<Club> function, 
                          Supplier<RuntimeException> notFoundException) {
    withLock(lock, () -> {
        Club club = find(clubId).orElseThrow(notFoundException);
        store.put(clubId, function.apply(club));  // Apply transformation inside lock
    });
}
```

**Benefits**:
- **Transaction boundary control**: Infrastructure layer controls transaction scope, not application layer
- **Atomic operations**: Lock acquisition and entity modification happen atomically
- **Distributed execution support**: Functions can be serialized and sent to distributed data grids (Apache Ignite, Hazelcast) for in-cluster execution
- **Cleaner separation**: Domain logic stays in domain layer, transaction management stays in infrastructure
- **Optimistic locking ready**: When adding JPA, the function can be retried on optimistic lock failures
- **Testability**: Easy to test transformation logic independently

**Use Cases**:
1. **Current (in-memory)**: Function executes within lock boundary
2. **Future (JPA)**: Function executes within `@Transactional` boundary
3. **Future (distributed cache)**: Function serialized and executed on cache node, avoiding network round-trips for read-modify-write

**Example with Distributed Cache**:
```java
// With Hazelcast/Ignite, this function executes ON the cache node
igniteCache.invoke(clubId, (entry, arguments) -> {
    Club club = entry.getValue();
    club.addJoinRequest(joinRequest);
    entry.setValue(club);
    return null;
});
```

**Trade-offs**:
- **Learning curve**: Developers must understand functional approach
- **Debugging**: Harder to step through code inside lambdas
- **Serialization constraints**: Functions must be serializable for distributed execution (future concern)

**Justification**:
This pattern provides maximum flexibility for future infrastructure choices without changing domain or application code. It's a small investment now that pays dividends when scaling or adding persistence.

**References**:
- Clean Architecture, Chapter 23: "Presenters and Humble Objects"
- Effective Java (3rd Edition), Item 44: "Favor the use of standard functional interfaces"
- Hazelcast IMDG Reference Manual: EntryProcessor pattern
- Apache Ignite Documentation: Compute Grid

---

## Summary

These architectural decisions prioritize:

1. **Domain understanding** over technical implementation
2. **Flexibility** over premature optimization
3. **Explicit design** over magic/convention
4. **Business rules** over infrastructure concerns

As the domain stabilizes, infrastructure decisions (database, API design, deployment model) can be made with full knowledge of requirements, avoiding costly refactoring.

The architecture is designed to support a small team (<5 developers) working on a monolith that can scale to microservices when needed, without rewriting business logic.

---

## References

- Martin, Robert C. "Clean Architecture: A Craftsman's Guide to Software Structure and Design." Prentice Hall, 2017.
- Evans, Eric. "Domain-Driven Design: Tackling Complexity in the Heart of Software." Addison-Wesley, 2003.
- Gamma, Erich, et al. "Design Patterns: Elements of Reusable Object-Oriented Software." Addison-Wesley, 1994.
- Bloch, Joshua. "Effective Java, 3rd Edition." Addison-Wesley, 2018.
