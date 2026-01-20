# Java 25 Upgrade Roadmap

## Executive Summary

This document outlines the roadmap for upgrading the Booking Engine from **Java 11** to **Java 25**. This is a major upgrade spanning 14 Java versions and 4 major releases (11 → 17 → 21 → 25), requiring careful dependency management and code modernization.

**Timeline**: 6-8 weeks
**Risk Level**: Medium-High
**Recommended Approach**: Incremental (11 → 17 → 21 → 25)

---

## Why Incremental Approach?

Jumping directly from Java 11 to 25 is risky because:
1. **Breaking changes accumulate** across multiple versions
2. **Dependency compatibility** varies by Java version
3. **Easier debugging** when issues arise
4. **Validation points** at each LTS version (17, 21)
5. **Team learning curve** for new language features

---

## Current State Analysis

### Current Configuration
- **Java Version**: 11 (openjdk64-11.0.26)
- **Spring Boot**: 2.7.14 (EOL November 2023)
- **Spring Framework**: 5.3.31
- **JUnit**: 5.10.1 (recently upgraded)
- **Cucumber**: 7.15.0 (recently upgraded)
- **Mockito**: 5.3.1

### Key Constraints
- Spring Boot 2.x requires Java 11-17 (does not support Java 21+)
- Spring Boot 3.x requires Java 17+ and Jakarta EE 9+
- Java 21+ requires Spring Boot 3.2+
- Java 25 is **non-LTS** (support until September 2026)

---

## Phase 1: Upgrade to Java 17 LTS + Spring Boot 3 (Week 1-2)

**Goal**: Establish Java 17 and Spring Boot 3 as stable baseline

**Understanding the Dependency**:
- Spring Boot 2.7.x supports Java 11-17 (but is EOL since November 2023)
- Spring Boot 3.x **requires** Java 17 minimum
- Therefore: You must upgrade Java first (or simultaneously)

**Recommended Approach**: Upgrade both together in one phase because:
1. Spring Boot 2.7 is EOL - no reason to stay on it
2. Reduces total migration time
3. Single validation checkpoint
4. Atomic change is easier to rollback if needed

**Alternative Approach** (if you prefer smaller steps):
1. First: Upgrade Java 11 → 17 (stay on Spring Boot 2.7 temporarily)
2. Validate everything works
3. Then: Upgrade Spring Boot 2.7 → 3.x
4. Validate again

### 1.1 Step 1: Update Java to 17

**Tasks**:
- [ ] Update `.java-version` to `17`
```bash
echo "17" > .java-version
```

- [ ] Update `pom.xml` Java version properties:
```xml
<properties>
    <java-source>17</java-source>
    <java-target>17</java-target>
    <java.version>17</java.version>
</properties>
```

- [ ] Remove deprecated compiler flags:
```xml
<!-- Remove from maven-surefire-plugin if present -->
<argLine>--illegal-access=permit</argLine>
```

**Verification after Java 17**:
```bash
mvn clean compile
mvn clean test
# All tests should still pass on Java 17 with Spring Boot 2.7
```

---

### 1.2 Step 2: Update Spring Boot to 3.2.x

**Prerequisites**: Java 17 must be installed (from Step 1)

**Rationale**: Spring Boot 3.x is required for Java 17+ long-term support

**Major Breaking Change**: `javax.*` → `jakarta.*` namespace migration

**Tasks**:

- [ ] Update Spring Boot version in `pom.xml`:
```xml
<spring-boot.version>3.2.2</spring-boot.version>
<spring.version>6.1.3</spring.version>
```

- [ ] Update Hibernate Validator:
```xml
<hibernate-validator.version>8.0.1.Final</hibernate-validator.version>
```

- [ ] Update Tomcat (included in Spring Boot 3):
```xml
<!-- Tomcat 10.x comes with Spring Boot 3 -->
<tomcat.version>10.1.18</tomcat.version>
```

- [ ] Replace all `javax.*` imports with `jakarta.*`:
```bash
# Automated replacement (review carefully!)
find . -name "*.java" -type f -exec sed -i '' 's/import javax\./import jakarta./g' {} \;
```

**Common Package Migrations**:
```java
// Before (javax)
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.servlet.*;

// After (jakarta)
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import jakarta.servlet.*;
```

**Migration Guide**: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide

---

### 1.3 Update Testing Dependencies

**Tasks**:

- [ ] Update JUnit (already on 5.10.1, verify compatibility):
```xml
<junit.version>5.10.2</junit.version>
<junit-platform.version>1.10.2</junit-platform.version>
```

- [ ] Update Mockito:
```xml
<mockito.version>5.10.0</mockito.version>
```

- [ ] Update AssertJ:
```xml
<assertj.version>3.25.3</assertj.version>
```

- [ ] Update Cucumber (already on 7.15.0, verify):
```xml
<cucumber.version>7.15.0</cucumber.version>
```

- [ ] Verify all tests pass:
```bash
mvn clean test
```

---

### 1.4 Update Other Dependencies

**Tasks**:

- [ ] Update Lombok (Java 17 support):
```xml
<lombok.version>1.18.30</lombok.version>
```

- [ ] Update Guava:
```xml
<guava.version>33.0.0-jre</guava.version>
```

- [ ] Update Jackson:
```xml
<jackson.version>2.16.1</jackson.version>
```

- [ ] Update SLF4J and Logback:
```xml
<slf4j.version>2.0.12</slf4j.version>
<logback.version>1.4.14</logback.version>
```

- [ ] Update Commons Lang3:
```xml
<commons-lang3.version>3.14.0</commons-lang3.version>
```

---

### 1.5 Validation Checkpoint

**Tasks**:
- [ ] Run full build: `mvn clean install`
- [ ] Run all tests: `mvn clean test`
- [ ] Verify application starts: `mvn spring-boot:run`
- [ ] Manual smoke testing of key features
- [ ] Check for deprecation warnings in logs
- [ ] Review and fix any compilation warnings

**Success Criteria**:
- ✅ All 207 tests passing
- ✅ No compilation errors
- ✅ Application starts successfully
- ✅ No critical deprecation warnings

---

## Phase 2: Adopt Java 17 Language Features (Week 2)

**Goal**: Modernize codebase with Java 17 features

### 2.1 Replace DTOs with Records

**Rationale**: Records are immutable, concise, and perfect for data carriers

**Status**: ✅ **COMPLETED** (January 2026)

**Completed Migrations**:

#### Event Classes (30 events across 8 components):
- ✅ **Booking component** (already completed)
  - `BookingCreatedEvent`
- ✅ **Slot component** (already completed)
  - `SlotCreatedEvent`
- ✅ **Auction component** (4 events)
  - `AuctionFinishedEvent`
  - `AuctionWinnerFoundEvent`
  - `AuctionStartedEvent`
  - `AuctionUnsuccessfulEvent`
- ✅ **Room component** (4 events)
  - `RoomCreatedEvent`
  - `SlotRequiredEvent`
  - `AuctionRequiredEvent`
  - `SlotReadyEvent`
- ✅ **Building component** (1 event)
  - `BuildingCreatedEvent`
- ✅ **Member component** (1 event)
  - `MemberCreatedEvent`
- ✅ **Club component** (4 events)
  - `ClubCreatedEvent`
  - `JoinRequestAcceptedEvent`
  - `JoinRequestCreatedEvent`
  - `JoinRequestDeniedEvent`
- ✅ **ClassManager component** (6 events)
  - `ClassCreatedEvent`
  - `ClassInstructorAddedEvent`
  - `ClassInstructorRemovedEvent`
  - `ClassRemovedEvent`
  - `RoomRegisteredForClassEvent`
  - `RoomUnregisteredForClassEvent`
- ✅ **SlotLifecycle component** (5 events)
  - `ClassReservationCreatedEvent`
  - `PersonReservationCreatedEvent`
  - `SlotCanBeMadeAvailableEvent`
  - `SlotRequiresAuctionEvent`
  - `SlotRequiresPreReservationEvent`

**Remaining Candidates** (classes in `api` packages):
- [ ] `BookingView`
- [ ] `ClubView`
- [ ] `RoomView`
- [ ] `SlotView`
- [ ] All `*Command` classes
- [ ] All `*Request` classes

**Example Migration**:
```java
// Before (Lombok)
@Data(staticConstructor = "of")
public class AuctionFinishedEvent implements Event {
    private final long auctionId;
}

// After (Java 17 Record)
public record AuctionFinishedEvent(long auctionId) implements Event {}
```

**Migration Changes**:
- Replaced `.of()` static factory methods with `new` record constructors
- Replaced `getFieldName()` methods with `fieldName()` record accessors
- Removed Lombok `@Data` annotations
- All 207 tests passing after migration

**Benefits Achieved**:
- Less boilerplate (no Lombok needed for event DTOs)
- Immutability guaranteed by compiler
- Pattern matching support (Java 21+)
- Better IDE support
- Cleaner, more maintainable code

---

### 2.2 Use Sealed Classes for State Hierarchies

**Rationale**: Sealed classes make type hierarchies explicit and enable exhaustive pattern matching

**Candidates**:
- `SlotState` hierarchy (CreatedSlotState, AvailableSlotState, PreReservedState, ReservedState)
- Any other state pattern implementations

**Example Migration**:
```java
// Before
interface SlotState { }
class CreatedSlotState implements SlotState { }
class AvailableSlotState implements SlotState { }
class PreReservedState implements SlotState { }
class ReservedState implements SlotState { }

// After (Java 17 Sealed)
sealed interface SlotState 
    permits CreatedSlotState, AvailableSlotState, PreReservedState, ReservedState {
}

final class CreatedSlotState implements SlotState { }
final class AvailableSlotState implements SlotState { }
final class PreReservedState implements SlotState { }
final class ReservedState implements SlotState { }
```

**Tasks**:
- [ ] Convert `SlotState` to sealed interface
- [ ] Mark implementations as `final`
- [ ] Update any instanceof checks
- [ ] Run tests to verify behavior unchanged

**Benefits**:
- Compiler ensures all states are handled
- Prevents external implementations
- Better IDE autocomplete
- Enables exhaustive pattern matching (Java 21+)

---

### 2.3 Pattern Matching for instanceof

**Rationale**: Cleaner code, eliminates explicit casts

**Example Migration**:
```java
// Before (Java 11)
if (exception instanceof SlotAlreadyReservedException) {
    SlotAlreadyReservedException e = (SlotAlreadyReservedException) exception;
    log.error("Slot {} already reserved by {}", e.getSlotId(), e.getPreviousUser());
}

// After (Java 17)
if (exception instanceof SlotAlreadyReservedException e) {
    log.error("Slot {} already reserved by {}", e.getSlotId(), e.getPreviousUser());
}
```

**Tasks**:
- [ ] Find all `instanceof` checks with explicit casts
- [ ] Refactor to pattern matching
- [ ] Run tests to verify behavior

---

### 2.4 Text Blocks for Multi-line Strings

**Rationale**: Better readability for error messages, JSON, SQL, etc.

**Example Migration**:
```java
// Before
String errorMessage = "Failed to reserve slot.\n" +
                     "Slot ID: " + slotId + "\n" +
                     "User ID: " + userId + "\n" +
                     "Reason: Slot already reserved";

// After (Java 17)
String errorMessage = """
    Failed to reserve slot.
    Slot ID: %d
    User ID: %d
    Reason: Slot already reserved
    """.formatted(slotId, userId);
```

**Tasks**:
- [ ] Identify multi-line string concatenations
- [ ] Convert to text blocks
- [ ] Use `formatted()` or `String.format()` for variables

---

### 2.5 Switch Expressions

**Rationale**: More concise and safer than switch statements

**Example Migration**:
```java
// Before (Java 11)
String status;
switch (state) {
    case CREATED:
        status = "created";
        break;
    case AVAILABLE:
        status = "available";
        break;
    case RESERVED:
        status = "reserved";
        break;
    default:
        throw new IllegalStateException("Unknown state: " + state);
}

// After (Java 17)
String status = switch (state) {
    case CREATED -> "created";
    case AVAILABLE -> "available";
    case RESERVED -> "reserved";
};
```

**Tasks**:
- [ ] Find switch statements that assign values
- [ ] Convert to switch expressions
- [ ] Leverage exhaustiveness with sealed types

---

## Phase 3: Upgrade to Java 21 LTS (Week 3-4)

**Goal**: Move to latest LTS version with advanced features

### 3.1 Update Java Runtime

**Tasks**:
- [ ] Update `.java-version` to `21`
- [ ] Update `pom.xml`:
```xml
<java-source>21</java-source>
<java-target>21</java-target>
<java.version>21</java.version>
```

- [ ] Verify build: `mvn clean compile test`

---

### 3.2 Update Spring Boot to 3.3.x

**Rationale**: Spring Boot 3.3+ has optimizations for Java 21

**Tasks**:
- [ ] Update Spring Boot:
```xml
<spring-boot.version>3.3.0</spring-boot.version>
<spring.version>6.1.8</spring.version>
```

- [ ] Run tests: `mvn clean test`

---

### 3.3 Update Dependencies for Java 21

**Tasks**:

- [ ] Update Lombok:
```xml
<lombok.version>1.18.32</lombok.version>
```

- [ ] Update testing libraries:
```xml
<junit.version>5.10.2</junit.version>
<mockito.version>5.11.0</mockito.version>
<assertj.version>3.25.3</assertj.version>
```

- [ ] Update other dependencies:
```xml
<guava.version>33.1.0-jre</guava.version>
<jackson.version>2.17.0</jackson.version>
```

---

## Phase 4: Adopt Java 21 Language Features (Week 4-5)

**Goal**: Leverage Java 21's powerful new features

### 4.1 Pattern Matching for Switch

**Rationale**: Combine type checking and extraction in switch expressions

**Example**:
```java
// Before (Java 17)
String description;
if (state instanceof CreatedSlotState) {
    description = "Slot created";
} else if (state instanceof AvailableSlotState) {
    description = "Slot available";
} else if (state instanceof ReservedState r) {
    description = "Reserved by user " + r.getUserId();
} else {
    description = "Unknown state";
}

// After (Java 21)
String description = switch (state) {
    case CreatedSlotState s -> "Slot created";
    case AvailableSlotState s -> "Slot available";
    case ReservedState r -> "Reserved by user " + r.getUserId();
    case PreReservedState p -> "Pre-reserved by user " + p.getUserId();
};
```

**Tasks**:
- [ ] Find instanceof chains
- [ ] Refactor to pattern matching switch
- [ ] Leverage sealed types for exhaustiveness
- [ ] Run tests to verify

**Benefits**:
- Compiler-verified exhaustiveness with sealed types
- More concise code
- Better null safety

---

### 4.2 Record Patterns

**Rationale**: Destructure records directly in patterns

**Example**:
```java
// Before (Java 17)
if (booking instanceof BookingView view) {
    long userId = view.userId();
    long slotId = view.slotId();
    processBooking(userId, slotId);
}

// After (Java 21)
if (booking instanceof BookingView(var id, var userId, var time, var slotId)) {
    processBooking(userId, slotId);
}

// Or in switch
String result = switch (event) {
    case BookingCreatedEvent(var bookingId, var userId, var slotId) ->
        "User %d booked slot %d".formatted(userId, slotId);
    case BookingCancelledEvent(var bookingId, var reason) ->
        "Booking %d cancelled: %s".formatted(bookingId, reason);
};
```

**Tasks**:
- [ ] Identify record destructuring opportunities
- [ ] Refactor to record patterns
- [ ] Use in switch expressions where applicable

---

### 4.3 Virtual Threads (Project Loom)

**Rationale**: Massive scalability for I/O-bound operations

**Configuration**:
```yaml
# application.yml
spring:
  threads:
    virtual:
      enabled: true
```

**Or programmatically**:
```java
@Configuration
public class VirtualThreadConfig {
    
    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }
}
```

**Tasks**:
- [ ] Enable virtual threads in Spring Boot
- [ ] Test with load testing tool (JMeter, Gatling)
- [ ] Monitor thread usage with JFR or VisualVM
- [ ] Measure performance improvements

**Benefits**:
- Handle millions of concurrent requests
- Simpler concurrency model (no reactive programming needed)
- Better resource utilization
- Backward compatible (no code changes required)

**Caution**:
- Don't use thread-local storage extensively
- Avoid pinning (synchronized blocks on monitors)
- Test thoroughly with production-like load

---

### 4.4 Sequenced Collections

**Rationale**: Unified API for collections with defined order

**New Methods**:
```java
// Java 21 adds to List, Set, Map
list.getFirst();
list.getLast();
list.addFirst(element);
list.addLast(element);
list.removeFirst();
list.removeLast();
list.reversed(); // Returns reversed view
```

**Example Migration**:
```java
// Before (Java 17)
List<Slot> slots = findSlots();
if (!slots.isEmpty()) {
    Slot first = slots.get(0);
    Slot last = slots.get(slots.size() - 1);
}

// After (Java 21)
List<Slot> slots = findSlots();
if (!slots.isEmpty()) {
    Slot first = slots.getFirst();
    Slot last = slots.getLast();
}
```

**Tasks**:
- [ ] Find `list.get(0)` patterns → `list.getFirst()`
- [ ] Find `list.get(list.size() - 1)` → `list.getLast()`
- [ ] Use `reversed()` instead of manual reversal

---

### 4.5 String Templates (Preview in Java 21)

**Note**: This is a preview feature in Java 21, finalized in later versions

**Enable preview features**:
```xml
<maven-compiler-plugin>
    <configuration>
        <compilerArgs>
            <arg>--enable-preview</arg>
        </compilerArgs>
    </configuration>
</maven-compiler-plugin>
```

**Example**:
```java
// Before
String message = String.format("User %d booked slot %d at %s", 
    userId, slotId, bookingTime);

// After (Java 21+ with preview)
String message = STR."User \{userId} booked slot \{slotId} at \{bookingTime}";
```

**Decision**: Evaluate if worth enabling preview features or wait for finalization

---

## Phase 5: Upgrade to Java 25 (Week 5-6)

**Goal**: Move to latest Java version

### 5.1 Important Considerations

**Java 25 Status**:
- **Release Date**: March 2025
- **Support**: **LTS (Long-Term Support)** - Supported until at least 2027
- **LTS Cadence**: Java now releases LTS versions every 2 years (17, 21, 25, 27...)
- **Recommendation**: Safe for production use as an LTS release

**Note**: Java 25 LTS provides long-term stability and support, making it suitable for production systems

---

### 5.2 Update Java Runtime

**Tasks**:
- [ ] Update `.java-version` to `25`
- [ ] Update `pom.xml`:
```xml
<java-source>25</java-source>
<java-target>25</java-target>
<java.version>25</java.version>
```

- [ ] Verify build: `mvn clean compile test`

---

### 5.3 Update Spring Boot to Latest

**Tasks**:
- [ ] Check Spring Boot compatibility with Java 25
- [ ] Update to latest Spring Boot 3.x:
```xml
<spring-boot.version>3.4.x</spring-boot.version>
```

- [ ] Run tests: `mvn clean test`

---

### 5.4 Update All Dependencies

**Tasks**:

- [ ] Update Lombok to latest:
```xml
<lombok.version>1.18.34</lombok.version>
```

- [ ] Update testing frameworks:
```xml
<junit.version>5.11.x</junit.version>
<mockito.version>5.14.x</mockito.version>
<assertj.version>3.26.x</assertj.version>
```

- [ ] Update other dependencies:
```xml
<guava.version>33.3.x-jre</guava.version>
<jackson.version>2.18.x</jackson.version>
<commons-lang3.version>3.17.x</commons-lang3.version>
```

- [ ] Check for any deprecated APIs in logs
- [ ] Review release notes for breaking changes

---

### 5.5 Adopt Java 25 Features

**Note**: Java 25 features depend on what's included in the release. Check JEP (JDK Enhancement Proposals) for details.

**Potential Features** (based on roadmap):
- String Templates (finalized)
- Unnamed Patterns and Variables (finalized)
- Scoped Values (finalized)
- Structured Concurrency (preview/finalized)

**Tasks**:
- [ ] Review Java 25 release notes
- [ ] Identify applicable features for the codebase
- [ ] Create proof-of-concept for new features
- [ ] Gradually adopt where beneficial

---

## Phase 6: Final Validation & Optimization (Week 6-8)

### 6.1 Comprehensive Testing

**Tasks**:
- [ ] Run full test suite: `mvn clean test`
- [ ] Run integration tests: `mvn verify -P integration-tests`
- [ ] Performance testing with JMH benchmarks
- [ ] Load testing (if applicable)
- [ ] Memory profiling with VisualVM/JFR
- [ ] Check for memory leaks

---

### 6.2 Code Quality Checks

**Tasks**:
- [ ] Run static analysis: `mvn spotbugs:check`
- [ ] Check test coverage: `mvn jacoco:report`
- [ ] Review deprecation warnings
- [ ] Update Javadoc for new features
- [ ] Code review for modernization opportunities

---

### 6.3 Documentation Updates

**Tasks**:
- [ ] Update README with Java 25 requirement
- [ ] Document new language features used
- [ ] Update build instructions
- [ ] Update deployment documentation
- [ ] Create migration notes for team

---

### 6.4 Rollback Plan

**Prepare for issues**:
- [ ] Tag current working version: `git tag java-11-stable`
- [ ] Document rollback procedure
- [ ] Keep Java 21 build available as fallback
- [ ] Test rollback process

---

## Dependency Version Matrix

### Target Versions for Java 25

| Dependency | Current (Java 11) | Java 17 | Java 21 | Java 25 |
|------------|-------------------|---------|---------|---------|
| Java | 11 | 17 | 21 | 25 |
| Spring Boot | 2.7.14 | 3.2.2 | 3.3.0 | 3.4.x |
| Spring Framework | 5.3.31 | 6.1.3 | 6.1.8 | 6.2.x |
| JUnit | 5.10.1 | 5.10.2 | 5.10.2 | 5.11.x |
| Mockito | 5.3.1 | 5.10.0 | 5.11.0 | 5.14.x |
| AssertJ | 3.24.2 | 3.25.3 | 3.25.3 | 3.26.x |
| Cucumber | 7.15.0 | 7.15.0 | 7.18.0 | 7.20.x |
| Lombok | 1.18.x | 1.18.30 | 1.18.32 | 1.18.34 |
| Guava | 31.x | 33.0.0 | 33.1.0 | 33.3.x |
| Jackson | 2.15.x | 2.16.1 | 2.17.0 | 2.18.x |
| Hibernate Validator | 6.2.5 | 8.0.1 | 8.0.1 | 8.0.x |
| SLF4J | 1.7.36 | 2.0.12 | 2.0.12 | 2.0.x |

---

## Breaking Changes to Watch For

### Java 11 → 17
- ✅ Removal of Nashorn JavaScript engine
- ✅ Removal of Applet API
- ✅ Strong encapsulation of JDK internals (use `--add-opens` if needed)
- ✅ Deprecation of Security Manager

### Java 17 → 21
- ✅ Removal of deprecated APIs
- ✅ Changes to garbage collectors (G1GC improvements)
- ✅ Stricter null handling in some APIs

### Java 21 → 25
- ⚠️ Check release notes (Java 25 not yet released)
- ⚠️ Potential removal of preview features from earlier versions
- ⚠️ API changes in incubator modules

---

## Testing Strategy

### Unit Tests
```bash
# Run all unit tests
mvn clean test

# Run with coverage
mvn clean test jacoco:report

# Check coverage report
open target/site/jacoco/index.html
```

### Integration Tests
```bash
# Run integration tests
mvn verify -P integration-tests
```

### Performance Testing
```bash
# Run JMH benchmarks (if you add them)
mvn clean install
java -jar target/benchmarks.jar
```

### Load Testing
```bash
# Example with Apache Bench
ab -n 10000 -c 100 http://localhost:8080/api/bookings

# Or use JMeter, Gatling, etc.
```

---

## Rollback Procedures

### If Issues Arise at Java 17
```bash
# Revert to Java 11
git checkout java-11-stable

# Update .java-version
echo "11" > .java-version

# Rebuild
mvn clean install
```

### If Issues Arise at Java 21
```bash
# Stay on Java 17 (LTS)
echo "17" > .java-version

# Revert Spring Boot to 3.2.x
# Edit pom.xml and rebuild
```

### If Issues Arise at Java 25
```bash
# Fallback to Java 21 (LTS)
echo "21" > .java-version

# Revert to Spring Boot 3.3.x
# Edit pom.xml and rebuild
```

---

## Risk Assessment

### High Risk Items
1. **Spring Boot 2 → 3 migration** (javax → jakarta)
   - Mitigation: Thorough testing, automated search/replace, staged rollout
   
2. **Virtual threads adoption**
   - Mitigation: Enable gradually, monitor performance, have rollback plan

3. **Java 25 non-LTS support**
   - Mitigation: Consider staying on Java 21 LTS for production

### Medium Risk Items
1. **Sealed classes refactoring**
   - Mitigation: Comprehensive test coverage, gradual adoption

2. **Record conversion**
   - Mitigation: Start with simple DTOs, verify serialization

### Low Risk Items
1. **Pattern matching adoption**
   - Mitigation: Straightforward refactoring, easy to revert

2. **Text blocks**
   - Mitigation: Cosmetic change, no runtime impact

---

## Success Criteria

### Phase 1 (Java 17)
- ✅ All 207 tests passing
- ✅ Application starts successfully
- ✅ No critical deprecation warnings
- ✅ Spring Boot 3 migration complete

### Phase 2 (Java 17 Features)
- ✅ At least 10 DTOs converted to records
- ✅ SlotState hierarchy sealed
- ✅ Pattern matching used in 5+ locations
- ✅ All tests still passing

### Phase 3 (Java 21)
- ✅ All tests passing on Java 21
- ✅ Virtual threads enabled and tested
- ✅ No performance regressions

### Phase 4 (Java 21 Features)
- ✅ Pattern matching for switch used in 5+ locations
- ✅ Record patterns adopted where applicable
- ✅ Sequenced collections used throughout

### Phase 5 (Java 25)
- ✅ All tests passing on Java 25
- ✅ Latest dependencies compatible
- ✅ New features documented

### Phase 6 (Final Validation)
- ✅ 80%+ test coverage maintained
- ✅ Performance benchmarks meet targets
- ✅ Documentation complete
- ✅ Team trained on new features

---

## Timeline

| Week | Phase | Focus | Deliverable |
|------|-------|-------|-------------|
| 1 | Phase 1 | Java 17 + Spring Boot 3 | Working build on Java 17 |
| 2 | Phase 2 | Java 17 features | Modernized code with records/sealed |
| 3 | Phase 3 | Java 21 upgrade | Working build on Java 21 |
| 4 | Phase 4 | Java 21 features | Virtual threads + pattern matching |
| 5 | Phase 5 | Java 25 upgrade | Working build on Java 25 |
| 6-8 | Phase 6 | Validation | Production-ready Java 25 codebase |

---

## Recommendation

### For Production Systems
**Upgrade to Java 25 LTS** (supported until at least 2027)
- Latest LTS release with long-term support
- All modern Java features (records, sealed classes, virtual threads, pattern matching)
- Better performance and security improvements
- Future-proof for next 2+ years

### Alternative: Java 21 LTS
**Stay on Java 21 LTS** (supported until September 2026)
- Proven stability in production
- Slightly more mature ecosystem
- Lower risk if you prefer waiting 6-12 months for Java 25 to mature
- Still receives all critical updates

---

## Next Steps

1. **Review this roadmap with the team**
2. **Decide on target version** (Java 21 LTS vs Java 25)
3. **Set up test environment** with target Java version
4. **Create feature branch**: `git checkout -b java-upgrade`
5. **Start with Phase 1**: Java 17 upgrade
6. **Test thoroughly at each phase**
7. **Document learnings and issues**
8. **Plan production deployment**

---

## Resources

### Official Documentation
- Java 17 Features: https://openjdk.org/projects/jdk/17/
- Java 21 Features: https://openjdk.org/projects/jdk/21/
- Java 25 Features: https://openjdk.org/projects/jdk/25/
- Spring Boot 3 Migration: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide
- Jakarta EE 9 Migration: https://jakarta.ee/specifications/platform/9/

### Tools
- OpenRewrite: Automated code refactoring (https://docs.openrewrite.org/)
- Eclipse Transformer: javax → jakarta migration (https://github.com/eclipse/transformer)
- JDeps: Analyze dependencies (included with JDK)

### Community
- Spring Boot GitHub: https://github.com/spring-projects/spring-boot
- JUnit 5 User Guide: https://junit.org/junit5/docs/current/user-guide/
- Java Reddit: https://reddit.com/r/java

---

## Appendix: Automated Migration Tools

### OpenRewrite Recipes

Add to `pom.xml`:
```xml
<plugin>
    <groupId>org.openrewrite.maven</groupId>
    <artifactId>rewrite-maven-plugin</artifactId>
    <version>5.20.0</version>
    <configuration>
        <activeRecipes>
            <recipe>org.openrewrite.java.migrate.JavaVersion17</recipe>
            <recipe>org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_2</recipe>
        </activeRecipes>
    </configuration>
</plugin>
```

Run migration:
```bash
mvn rewrite:run
```

### Eclipse Transformer (javax → jakarta)

```bash
# Download transformer
wget https://repo1.maven.org/maven2/org/eclipse/transformer/org.eclipse.transformer.cli/0.5.0/org.eclipse.transformer.cli-0.5.0.jar

# Transform source files
java -jar org.eclipse.transformer.cli-0.5.0.jar \
  src/main/java \
  transformed-src \
  -tr jakarta
```

**Note**: Review all automated changes carefully before committing!

---

## Conclusion

This roadmap provides a structured approach to upgrading from Java 11 to Java 25. The incremental strategy minimizes risk while allowing you to adopt modern Java features progressively. 

**Key Takeaways**:
1. Don't skip LTS versions (17, 21)
2. Test thoroughly at each phase
3. Adopt new features gradually
4. Consider staying on Java 21 LTS for production stability
5. Keep rollback options available

Good luck with your migration! 🚀
