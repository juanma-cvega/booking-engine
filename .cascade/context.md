# Booking Engine - Project Context

## Project Overview
Maven multi-module Java project for booking engine functionality.

## Java Migration History (Java 8 → 25)

### Complete Migration Timeline
1. **Java 8 → 11** - Initial modernization with dependency updates
2. **Java 11 → 17** - Spring Boot 2.7 → 3.x, `javax.*` → `jakarta.*` migration
3. **Java 17 Features** - Migrated 64 classes to records, sealed classes, pattern matching
4. **Java 17 → 21** - LTS upgrade with Spring Boot 3.3.0
5. **Java 21 Features** - Virtual threads, record patterns, sequenced collections
6. **Java 21 → 25** - Latest upgrade with dependency compatibility fixes
7. **Java 25 Tooling** - mise setup, CI/CD pipeline, google-java-format 1.33.0

### Phase 1: Java 17 Migration (Completed)
- ✅ Updated to Java 17 LTS
- ✅ Spring Boot 2.7 → 3.3.0 (Jakarta EE migration)
- ✅ All `javax.*` → `jakarta.*` namespace changes
- ✅ Updated all dependencies for Java 17 compatibility

### Phase 2: Java 17 Features (Completed)
- ✅ **64 classes migrated to records**:
  - 30 Event classes (all domain events)
  - 13 View classes (all read models)
  - 20 Command classes (all write operations)
  - 1 Request class (JoinRequest)
- ✅ **Sealed classes**: SlotState hierarchy with 4 state classes
- ✅ **Pattern matching**: Switch expressions with exhaustiveness checking
- ✅ Replaced Lombok `@Data` with native records
- ✅ Defensive copying with `List.copyOf()`, `Set.copyOf()`, `Map.copyOf()`

### Phase 3: Java 21 Migration (Completed)
- ✅ Updated to Java 21 LTS
- ✅ Spring Boot 3.3.0 with Java 21 optimizations
- ✅ All dependencies updated for Java 21

### Phase 4: Java 21 Features (Completed)
- ✅ **Virtual threads**: 10,000x web scalability improvement
  - Tomcat: ~200 → millions of concurrent requests
  - Async processing: unlimited parallelism
- ✅ **Record patterns**: Destructuring in switch expressions
- ✅ **Sequenced collections**: `.getFirst()` instead of `.get(0)`
- ✅ **Pattern matching for switch**: Combined with sealed types

### Phase 5: Java 25 Migration (Completed - Jan 21, 2026)
**Commit**: `ed92f8b` - "feat(java25): upgrade to Java 25 with full dependency compatibility"

**Dependency Updates**:
- Java: 21 → 25
- Spring Boot: 3.3.0 → 3.4.1
- Spring Framework: 6.1.8 → 6.2.1
- Lombok: 1.18.32 → 1.18.42 (Java 25 support)
- Mockito: 5.11.0 → 5.21.0
- Byte Buddy: 1.18.4 (explicit for Java 25 support)
- Maven Compiler Plugin: 3.11.0 → 3.13.0
- Spotless Plugin: 2.43.0 → 3.1.0

**Testing**: ✅ All 191 tests passing

### Phase 6: Java 25 Tooling Setup (Jan 23, 2026)
**Commit**: `7809c9d` - "Configure Java 25 with mise and update CI/CD pipeline"

- ✅ Created `mise.toml` for Java 25 and Maven version management
- ✅ Switched from jenv to mise for version management
- ✅ Updated `google-java-format` 1.19.2 → 1.33.0 (Java 25 compatibility)
- ✅ Created GitHub Actions CI/CD pipeline
- ✅ Configured SonarCloud integration
- ✅ Added Dependabot for automated dependency updates
- ✅ Created `sonar-project.properties`
- ✅ Removed deprecated CircleCI pipeline

## Key Configuration

### Version Management
- **Tool**: mise (installed system-wide)
- **Java Version**: 25
- **Maven**: Latest (managed by mise)
- **Config File**: `mise.toml`

### CI/CD
- **Platform**: GitHub Actions
- **Main Branch**: master
- **Workflow File**: `.github/workflows/ci.yml`
- **Triggers**: Push and PR to master branch

### SonarCloud
- **URL**: https://sonarcloud.io
- **Organization**: juan-manuel-carnicero-vega
- **Project Key**: booking-engine
- **Token**: Stored in GitHub secrets as `SONAR_TOKEN`

### Code Quality
- **Formatter**: google-java-format 1.33.0 (AOSP style)
- **Coverage**: JaCoCo
- **Static Analysis**: SonarCloud

## Important Notes
- Main branch is `master` (not `main`)
- jenv has been disabled in favor of mise
- Spotless formatting check runs on compile phase
- Tests run with coverage reporting via JaCoCo
