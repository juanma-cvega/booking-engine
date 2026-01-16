# Booking Engine - Modernization Plan

## Overview

This document outlines the roadmap for modernizing the Booking Engine codebase with current technology while preserving the sound architectural decisions already in place. The plan respects the domain-first approach and will only add infrastructure when the domain model has stabilized.

---

## Guiding Principles

1. **Preserve domain integrity**: Do not compromise domain model for technical convenience
2. **Maintain architectural flexibility**: Keep deployment options open
3. **Incremental adoption**: Modernize in phases to minimize risk
4. **Team capacity**: Plan assumes team of <5 developers
5. **Domain stability first**: Infrastructure follows domain, not vice versa

---

## Phase 1: Language & Framework Modernization (2-3 weeks)

**Goal**: Upgrade to modern Java and Spring versions without changing architecture.

### 1.1 Upgrade to Java 17 LTS

**Rationale**: Java 17 is LTS (supported until 2029) and provides language features that improve domain modeling.

**Tasks**:
- [ ] Update `.java-version` to Java 17
- [ ] Update `pom.xml`: `<java.version>17</java.version>`
- [ ] Remove `--illegal-access=permit` flag from surefire configuration
- [ ] Test all builds and verify no breaking changes

**Benefits**:
- Records for immutable value objects
- Sealed classes for type hierarchies
- Pattern matching for cleaner code
- Text blocks for better readability
- Improved NullPointerException messages

**Risk**: Low - Java 11 to 17 is mostly backward compatible

---

### 1.2 Upgrade Spring Boot to 3.3.x

**Rationale**: Spring Boot 2.7.x reached end-of-life in November 2023. Version 3.3.x provides security patches and performance improvements.

**Tasks**:
- [ ] Update `pom.xml`: `<spring-boot.version>3.3.x</spring-boot.version>`
- [ ] Replace `javax.*` imports with `jakarta.*` (Jakarta EE 9+)
- [ ] Update Hibernate Validator to 8.x
- [ ] Update Tomcat to 10.x (included in Spring Boot 3)
- [ ] Test all Spring configurations and bean creation

**Migration Guide**: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide

**Benefits**:
- Security patches and bug fixes
- Better native image support (GraalVM)
- Performance improvements
- Continued vendor support

**Risk**: Medium - Requires javax → jakarta package changes

---

### 1.3 Upgrade Testing Frameworks

**Rationale**: JUnit 4 was superseded by JUnit 5 in 2017. Modern testing tools provide better expressiveness.

**Tasks**:
- [ ] Update `pom.xml`: `<junit.version>5.11.x</junit.version>`
- [ ] Migrate test annotations:
  - `@Test` → `@Test` (same but different package)
  - `@Before` → `@BeforeEach`
  - `@After` → `@AfterEach`
  - `@BeforeClass` → `@BeforeAll`
  - `@AfterClass` → `@AfterAll`
- [ ] Update Cucumber to 7.x
- [ ] Update Mockito to latest (already on 5.x)
- [ ] Update AssertJ to latest (already on 3.x)

**Benefits**:
- Parameterized tests with `@ParameterizedTest`
- Nested test classes with `@Nested`
- Better test lifecycle management
- Display names for better test reports
- Conditional test execution

**Risk**: Low - Mostly annotation changes

---

### 1.4 Update Other Dependencies

**Tasks**:
- [ ] Update Lombok to 1.18.34 (latest)
- [ ] Update Guava to 33.x
- [ ] Update Jackson to 2.17.x
- [ ] Update Commons Lang3 to 3.14.x
- [ ] Update SLF4J and Logback to latest

**Risk**: Low - Patch version updates

---

## Phase 2: Modern Java Features in Domain Layer (2-3 weeks)

**Goal**: Leverage Java 17+ features to improve domain code quality without changing architecture.

### 2.1 Replace DTOs with Records

**Rationale**: Records are immutable by default and reduce boilerplate for data carriers.

**Example**:
```java
// Before (Lombok @Data)
@Data
@AllArgsConstructor
public class BookingView {
    private final long id;
    private final long userId;
    private final ZonedDateTime bookingTime;
    private final long slotId;
}

// After (Java Record)
public record BookingView(
    long id,
    long userId,
    ZonedDateTime bookingTime,
    long slotId
) {}
```

**Tasks**:
- [ ] Identify all view/DTO classes (classes in `api` packages)
- [ ] Convert to records (keep domain entities as classes)
- [ ] Update tests to use record constructors
- [ ] Verify serialization still works (Jackson supports records)

**Benefits**:
- Less boilerplate
- Immutability guaranteed
- Pattern matching support
- Better semantics (clearly data carriers)

**Risk**: Low - Records are well-supported by Jackson and Spring

---

### 2.2 Use Sealed Classes for State Hierarchies

**Rationale**: Sealed classes make state hierarchies explicit and enable exhaustive pattern matching.

**Example**:
```java
// Before
interface SlotState { }
class CreatedSlotState implements SlotState { }
class AvailableSlotState implements SlotState { }
class PreReservedState implements SlotState { }
class ReservedState implements SlotState { }

// After
sealed interface SlotState 
    permits CreatedSlotState, AvailableSlotState, PreReservedState, ReservedState { }

final class CreatedSlotState implements SlotState { }
final class AvailableSlotState implements SlotState { }
final class PreReservedState implements SlotState { }
final class ReservedState implements SlotState { }
```

**Tasks**:
- [ ] Convert `SlotState` hierarchy to sealed interface
- [ ] Convert other state hierarchies (if any)
- [ ] Use pattern matching in switch expressions

**Benefits**:
- Compiler ensures all states are handled
- Prevents external implementations
- Better IDE support
- Self-documenting code

**Risk**: Low - Straightforward refactoring

---

### 2.3 Pattern Matching and Switch Expressions

**Rationale**: Cleaner, more expressive code for type-based logic.

**Example**:
```java
// Before
if (state instanceof CreatedSlotState) {
    return ((CreatedSlotState) state).makeAvailable();
} else if (state instanceof AvailableSlotState) {
    return ((AvailableSlotState) state).reserve(user);
}
// ...

// After
return switch(state) {
    case CreatedSlotState s -> s.makeAvailable();
    case AvailableSlotState s -> s.reserve(user);
    case PreReservedState s -> s.handlePreReserved();
    case ReservedState s -> s.handleReserved();
};
```

**Tasks**:
- [ ] Identify instanceof chains and if-else type checks
- [ ] Refactor to pattern matching switch expressions
- [ ] Leverage exhaustiveness checking with sealed types

**Benefits**:
- More concise code
- Compiler-verified exhaustiveness
- Better null safety
- Clearer intent

**Risk**: Low - Improves existing code

---

### 2.4 Text Blocks for Multi-line Strings

**Rationale**: Better readability for exception messages, JSON, etc.

**Tasks**:
- [ ] Identify multi-line strings (error messages, test data)
- [ ] Convert to text blocks where appropriate
- [ ] Improve formatting of complex messages

**Risk**: Very Low - Cosmetic improvement

---

## Phase 3: Enhanced Domain Testing (2-3 weeks)

**Goal**: Increase confidence in domain logic through comprehensive testing.

### 3.1 Expand Unit Test Coverage

**Tasks**:
- [ ] Run JaCoCo coverage report: `mvn clean test jacoco:report`
- [ ] Target 80%+ coverage for domain entities and components
- [ ] Add missing test cases for edge conditions
- [ ] Use JUnit 5 parameterized tests for multiple scenarios

**Example**:
```java
@ParameterizedTest
@ValueSource(longs = {1L, 2L, 3L})
void shouldReserveSlotForDifferentUsers(long userId) {
    // Test with multiple user IDs
}
```

**Risk**: Low - Improves quality

---

### 3.2 Property-Based Testing with jqwik

**Rationale**: Discover edge cases automatically by testing properties rather than examples.

**Tasks**:
- [ ] Add jqwik dependency
- [ ] Identify domain invariants to test
- [ ] Write property-based tests for critical logic

**Example**:
```java
@Property
void slotCannotBeReservedTwice(@ForAll Slot slot, @ForAll SlotUser user1, @ForAll SlotUser user2) {
    Slot reserved = slot.reserve(clock, user1);
    assertThatThrownBy(() -> reserved.reserve(clock, user2))
        .isInstanceOf(SlotAlreadyReservedException.class);
}
```

**Benefits**:
- Discovers edge cases developers miss
- Tests invariants, not just examples
- Generates random test data

**Risk**: Low - Additive testing approach

---

### 3.3 Mutation Testing with PIT

**Rationale**: Verify that tests actually catch bugs by introducing mutations.

**Tasks**:
- [ ] Add PIT Maven plugin
- [ ] Run mutation testing: `mvn test-compile org.pitest:pitest-maven:mutationCoverage`
- [ ] Improve tests to kill surviving mutants
- [ ] Target 80%+ mutation coverage

**Benefits**:
- Validates test quality, not just coverage
- Finds weak tests
- Improves confidence in test suite

**Risk**: Low - Analysis tool, doesn't change code

---

### 3.4 Complete Cucumber Scenarios

**Tasks**:
- [ ] Review all feature files for completeness
- [ ] Add missing scenarios for error cases
- [ ] Ensure all business rules are documented
- [ ] Use Cucumber reports for living documentation

**Risk**: Low - Documentation improvement

---

### 3.5 Evaluate Testing Strategy Alternatives

**Context**: The current Cucumber-based BDD testing approach has proven cumbersome and brittle during framework migrations (JUnit 4→5, Cucumber 6→7). The complexity of maintaining Gherkin feature files, step definitions, and DataTable conversions creates friction when upgrading Java versions and testing frameworks, which contradicts the architectural principle of depending on standard runtime features.

**Current Pain Points**:
- Framework coupling: Cucumber upgrades require extensive refactoring of step definitions
- DataTable API changes break tests across versions
- Complex setup reduces test maintainability
- Migration complexity distracts from domain logic
- Heavy dependency on third-party libraries with varying support lifecycles

**Design Goals for Alternative Approach**:
1. **Top-down testing**: Maintain ability to test domain logic through complete use case flows, not just isolated unit tests
2. **Descriptive language**: Preserve human-readable test descriptions similar to Gherkin, but without external DSL dependencies
3. **Minimal dependencies**: Rely primarily on JDK standard features and well-supported, stable libraries
4. **Migration resilience**: Tests should survive Java version upgrades with minimal changes
5. **Domain focus**: Testing infrastructure should be invisible; tests should read like domain specifications

**Potential Alternatives to Evaluate**:

**Option A: JUnit 5 Nested Tests with Display Names**
```java
@DisplayName("Booking a slot")
class BookingUseCaseTest {
    
    @Nested
    @DisplayName("Given an available slot")
    class WhenSlotIsAvailable {
        
        @Test
        @DisplayName("When a user books the slot, then the slot should be reserved")
        void shouldReserveSlot() {
            // Arrange: Set up club, building, room, slot
            // Act: Execute booking use case
            // Assert: Verify slot is reserved and event published
        }
        
        @Test
        @DisplayName("When the slot is not open yet, then booking should fail")
        void shouldFailWhenSlotNotOpen() {
            // Test scenario
        }
    }
}
```
**Pros**: Pure JUnit 5, excellent IDE support, nested structure mirrors Gherkin scenarios
**Cons**: Less readable than Gherkin for non-technical stakeholders

**Option B: Fluent Test DSL with Builder Pattern**
```java
@Test
void shouldBookAvailableSlot() {
    given()
        .club(1).withAdmin(1)
        .building(1).inClub(1)
        .room(1).inBuilding(1)
        .slot().inRoom(1).availableAt("10:00")
    .when()
        .user(2).booksSlot(1)
    .then()
        .slotShouldBeReservedBy(2)
        .eventShouldBePublished(SlotReservedEvent.class);
}
```
**Pros**: Readable, type-safe, pure Java, no external DSL
**Cons**: Requires custom test infrastructure (but under our control)

**Option C: Approval Testing for Complex Scenarios**
```java
@Test
void completeBookingWorkflow() {
    // Execute complete workflow
    String result = executeBookingScenario(testData);
    
    // Compare against approved baseline
    Approvals.verify(result);
}
```
**Pros**: Captures complex interactions, easy to review changes
**Cons**: Requires approval file management

**Option D: Specification by Example with Pure Java**
```java
class BookingSpecification {
    
    @Test
    void specification() {
        Scenario.describe("User books an available slot")
            .given("A club with id 1 exists")
            .and("A building with id 1 exists in club 1")
            .and("A room with id 1 exists in building 1")
            .and("A slot is available in room 1 at 10:00")
            .when("User 2 books the slot")
            .then("The slot should be reserved by user 2")
            .and("A SlotReservedEvent should be published")
            .verify(this::executeAndVerify);
    }
    
    private void executeAndVerify(ScenarioContext ctx) {
        // Implementation
    }
}
```
**Pros**: Readable, documents behavior, pure Java
**Cons**: Custom framework needed (but lightweight)

**Evaluation Criteria**:
- [ ] Readability for domain experts
- [ ] Maintainability during framework upgrades
- [ ] IDE support and debugging experience
- [ ] Test execution speed
- [ ] Learning curve for team
- [ ] Dependency footprint
- [ ] Ability to generate living documentation

**Tasks**:
- [ ] Prototype 2-3 alternatives with existing test scenarios
- [ ] Evaluate against criteria above
- [ ] Measure migration effort from Cucumber
- [ ] Assess team feedback on readability
- [ ] Document decision in design-decisions.md
- [ ] Plan gradual migration if alternative is chosen

**Decision Deadline**: End of Phase 3

**Risk**: Medium - Requires investment in test infrastructure, but reduces long-term maintenance burden

---

## Phase 4: Resolve Domain TODOs (1-2 weeks)

**Goal**: Finalize domain model based on learnings.

### 4.1 Member Role Management

**Current TODO**: Should Member contain role information?

**Tasks**:
- [ ] Analyze current admin list usage in Club
- [ ] Design Member role model (enum, value object, or entity)
- [ ] Refactor Club to use Member roles
- [ ] Update tests and Cucumber scenarios
- [ ] Document decision in design-decisions.md

**Risk**: Medium - Domain model change

---

### 4.2 JoinRequest Component Extraction

**Current TODO**: Should JoinRequest be its own component?

**Tasks**:
- [ ] Analyze JoinRequest lifecycle and complexity
- [ ] Decide if extraction is warranted (does it have independent business rules?)
- [ ] If yes: Create JoinRequestManagerComponent
- [ ] If no: Document why it remains part of ClubManagerComponent
- [ ] Update design-decisions.md

**Risk**: Medium - Potential architectural change

---

## Phase 5: Infrastructure Implementation (3-4 weeks)

**Goal**: Add persistence and API layers once domain is stable.

### 5.1 Add Database Persistence

**Rationale**: Domain model is stable; ready for production persistence.

**Technology Choice**: Spring Data JPA with PostgreSQL

**Tasks**:
- [ ] Add Spring Data JPA and PostgreSQL dependencies
- [ ] Create JPA entity mappings (separate from domain entities)
- [ ] Implement JPA repositories
- [ ] Add Flyway or Liquibase for migrations
- [ ] Replace in-memory repositories with JPA implementations via DI
- [ ] Use `@Transactional` for transaction management
- [ ] Add optimistic locking with `@Version`
- [ ] Configure connection pooling (HikariCP)

**Migration Strategy**:
1. Keep in-memory implementations
2. Add JPA implementations alongside
3. Switch via Spring profiles
4. Remove in-memory once validated

**Benefits**:
- Production-ready persistence
- ACID transactions
- Query optimization
- Data durability

**Risk**: Medium - Infrastructure addition

---

### 5.2 Implement REST API

**Tasks**:
- [ ] Create REST controllers using use cases
- [ ] Add OpenAPI/Swagger documentation
- [ ] Implement proper error handling with `@ControllerAdvice`
- [ ] Add request/response validation with Bean Validation
- [ ] Implement HATEOAS if needed
- [ ] Add API versioning strategy
- [ ] Document API in docs folder

**Example**:
```java
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    
    private final CreateBookingUseCase createBookingUseCase;
    
    @PostMapping
    public ResponseEntity<BookingView> createBooking(@Valid @RequestBody CreateBookingRequest request) {
        CreateBookingCommand command = // map from request
        BookingView booking = createBookingUseCase.book(command);
        return ResponseEntity.created(/* location */).body(booking);
    }
}
```

**Risk**: Low - Well-understood pattern

---

### 5.3 Add Integration Tests

**Tasks**:
- [ ] Add Testcontainers for PostgreSQL
- [ ] Write `@SpringBootTest` integration tests
- [ ] Test REST API with MockMvc or REST Assured
- [ ] Test database transactions and rollback
- [ ] Test event publishing and listening
- [ ] Add to separate Maven profile (integration-tests)

**Example**:
```java
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class BookingControllerIT {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldCreateBooking() throws Exception {
        mockMvc.perform(post("/api/v1/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(/* JSON */))
            .andExpect(status().isCreated());
    }
}
```

**Risk**: Low - Standard testing approach

---

### 5.4 Replace Custom Event Publisher with Spring Events

**Tasks**:
- [ ] Evaluate if Spring's `ApplicationEventPublisher` meets needs
- [ ] If yes: Migrate to Spring Events
- [ ] If no: Keep custom `MessagePublisher` (e.g., for future message broker)
- [ ] Update listeners to use `@EventListener`
- [ ] Document decision

**Risk**: Low - Internal refactoring

---

## Phase 6: Observability & Production Readiness (2-3 weeks)

**Goal**: Make system production-ready with monitoring and operational features.

### 6.1 Add Observability

**Tasks**:
- [ ] Add Spring Boot Actuator
- [ ] Configure health checks (`/actuator/health`)
- [ ] Add Micrometer metrics
- [ ] Expose Prometheus endpoint (`/actuator/prometheus`)
- [ ] Add custom business metrics (bookings created, slots reserved, etc.)
- [ ] Configure structured logging (Logback with JSON encoder)
- [ ] Add correlation IDs for request tracing

**Risk**: Low - Non-invasive additions

---

### 6.2 Add Security

**Tasks**:
- [ ] Add Spring Security dependency
- [ ] Implement JWT or OAuth2 authentication
- [ ] Add authorization checks (replace manual admin checks)
- [ ] Secure actuator endpoints
- [ ] Add CORS configuration
- [ ] Implement rate limiting
- [ ] Add security headers

**Risk**: Medium - Requires authentication strategy decision

---

### 6.3 Error Handling & Validation

**Tasks**:
- [ ] Implement global exception handler with `@ControllerAdvice`
- [ ] Standardize error response format (RFC 7807 Problem Details)
- [ ] Add comprehensive input validation
- [ ] Add validation groups for different scenarios
- [ ] Document error codes

**Risk**: Low - Standard practice

---

### 6.4 Documentation

**Tasks**:
- [ ] Generate OpenAPI documentation
- [ ] Create API usage guide
- [ ] Document deployment procedures
- [ ] Create runbook for operations
- [ ] Update README with setup instructions

**Risk**: Very Low - Documentation only

---

## Phase 7: Advanced Features (Future)

**Goal**: Add advanced capabilities when needed.

### 7.1 Caching Strategy

**Tasks**:
- [ ] Identify cacheable queries (room availability, slot lists)
- [ ] Add Spring Cache abstraction
- [ ] Choose cache provider (Caffeine, Redis)
- [ ] Implement cache invalidation on events
- [ ] Monitor cache hit rates

**Trigger**: Performance testing shows database bottlenecks

---

### 7.2 Asynchronous Processing

**Tasks**:
- [ ] Identify long-running operations
- [ ] Add `@Async` for background tasks
- [ ] Configure thread pools
- [ ] Consider virtual threads (Java 21)
- [ ] Add async event processing if needed

**Trigger**: Operations take >500ms and block users

---

### 7.3 Message Broker Integration

**Tasks**:
- [ ] Evaluate need for distributed events
- [ ] Choose message broker (RabbitMQ, Kafka)
- [ ] Implement `MessagePublisher` with broker
- [ ] Add dead letter queues
- [ ] Implement idempotent event handlers

**Trigger**: Microservices decomposition or high event volume

---

### 7.4 Microservices Decomposition

**Tasks**:
- [ ] Identify bounded contexts for extraction
- [ ] Extract component to separate service
- [ ] Implement remote component interface (gRPC/REST)
- [ ] Update DI configuration to use remote implementation
- [ ] Add circuit breakers (Resilience4j)
- [ ] Implement distributed tracing (OpenTelemetry)

**Trigger**: Team grows >10 developers or scaling needs emerge

---

## Phase 8: Java 21 Adoption (Future)

**Goal**: Leverage latest Java LTS features.

### 8.1 Virtual Threads

**Tasks**:
- [ ] Upgrade to Java 21
- [ ] Enable virtual threads for Spring Boot
- [ ] Configure virtual thread executor
- [ ] Test performance improvements
- [ ] Monitor thread usage

**Benefits**:
- Massive scalability for I/O-bound operations
- Simpler concurrency model
- Better resource utilization

**Trigger**: Java 21 is widely adopted and stable (2025+)

---

### 8.2 Other Java 21 Features

**Tasks**:
- [ ] Use sequenced collections
- [ ] Adopt record patterns
- [ ] Use string templates (preview)
- [ ] Evaluate unnamed patterns and variables

**Trigger**: Java 21 adoption

---

## Migration Checklist

### Before Starting Each Phase:
- [ ] Create feature branch
- [ ] Review current state
- [ ] Ensure tests pass
- [ ] Document baseline metrics

### During Phase:
- [ ] Make incremental commits
- [ ] Keep tests passing
- [ ] Update documentation
- [ ] Review with team

### After Completing Phase:
- [ ] Run full test suite
- [ ] Update this document
- [ ] Create pull request
- [ ] Deploy to staging
- [ ] Validate functionality
- [ ] Merge to main

---

## Success Metrics

### Code Quality:
- [ ] 80%+ test coverage (JaCoCo)
- [ ] 80%+ mutation coverage (PIT)
- [ ] Zero critical security vulnerabilities (Snyk/Dependabot)
- [ ] SonarQube quality gate passing

### Performance:
- [ ] API response time <200ms (p95)
- [ ] Database query time <50ms (p95)
- [ ] Test suite runs in <5 minutes

### Operational:
- [ ] 99.9% uptime
- [ ] <1% error rate
- [ ] Monitoring dashboards in place
- [ ] Automated deployments working

---

## Risk Mitigation

### Technical Risks:
- **Breaking changes in upgrades**: Thorough testing, staging environment
- **Performance regressions**: Benchmark before/after, load testing
- **Data migration issues**: Flyway migrations, backup/restore procedures

### Team Risks:
- **Knowledge gaps**: Pair programming, documentation, training sessions
- **Scope creep**: Stick to phases, defer nice-to-haves
- **Burnout**: Reasonable timelines, celebrate milestones

### Business Risks:
- **Feature freeze during migration**: Incremental approach allows parallel feature work
- **Downtime**: Blue-green deployments, feature flags
- **Budget overruns**: Time-box phases, prioritize ruthlessly

---

## Timeline Summary

| Phase | Duration | Priority | Dependencies |
|-------|----------|----------|--------------|
| 1. Language & Framework | 2-3 weeks | High | None |
| 2. Modern Java Features | 2-3 weeks | High | Phase 1 |
| 3. Enhanced Testing | 2-3 weeks | High | Phase 2 |
| 4. Domain TODOs | 1-2 weeks | High | Phase 3 |
| 5. Infrastructure | 3-4 weeks | High | Phase 4 |
| 6. Production Readiness | 2-3 weeks | High | Phase 5 |
| 7. Advanced Features | Variable | Medium | Phase 6 |
| 8. Java 21 | 1-2 weeks | Low | Phase 6 |

**Total Core Modernization**: 12-18 weeks (3-4.5 months)

---

## Conclusion

This modernization plan respects the existing architectural decisions while bringing the codebase up to modern standards. The phased approach allows the team to:

1. Modernize incrementally without big-bang rewrites
2. Maintain working software throughout
3. Defer infrastructure decisions until domain stabilizes
4. Scale the architecture when business needs demand it

The plan prioritizes domain integrity and team productivity over chasing the latest trends. Each phase delivers value and can be adjusted based on learnings and changing priorities.

---

## References

- Spring Boot 3 Migration Guide: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide
- JUnit 5 User Guide: https://junit.org/junit5/docs/current/user-guide/
- Java 17 Features: https://openjdk.org/projects/jdk/17/
- Java 21 Features: https://openjdk.org/projects/jdk/21/
- Clean Architecture (Robert C. Martin)
- Building Microservices (Sam Newman)
