# Booking Engine - Project Context

## Project Overview
A Java 25 booking engine project using Maven multi-module architecture, Spring Boot 4.0.1, and modern Java features.

## Recent Changes (Last 2 Weeks)

### Latest Commit: fix: resolve awk multi-line string issue in pre-commit hook
**Date:** Jan 24, 2026

**Staged files:**
- .cascade/context.md

---

### January 24, 2026
- **fix**: Resolved awk multi-line string issue in pre-commit hook (2293a5e)
- **docs**: Initialized context.md with project overview (c13c8fa)
- **docs**: Updated context.md with recent changes (56229bb)
- **chore**: Removed obsolete Jenkinsfile (85c4a2f)
- **ci**: Added explicit Maven dependency caching to all jobs (fbacc17)

### January 23, 2026

#### CI/CD & Infrastructure
- Configured Java 25 with mise and updated CI/CD pipeline (7809c9d)
- Removed CircleCI configuration (5c3c46a)
- Bumped actions/checkout from 4 to 6 (31017ae)
- Bumped actions/cache from 4 to 5 (8e4a174)
- Bumped actions/setup-java from 4 to 5 (29a114a)
- Bumped actions/upload-artifact from 4 to 6 (3e0069a)
- Bumped dorny/test-reporter from 1 to 2 (f342112)
- Fixed SonarCloud project key and organization ID (bc9bc53, 422033d)
- Added workflow permissions for test reporter (67d9a59)
- Removed redundant JaCoCo check and Maven cache configuration (6b4866b, 7f08987)

#### Git Hooks
- Added version-controlled Git hooks with context.md auto-update (7153552)
- Added test execution to pre-commit hook (afdc8ec)
- Fixed Spotless check ordering in pre-commit hook (0bf65ed)

#### Code Quality & Testing
- Migrated UseCaseCTest to JUnit 5 Platform Suite API (1840a00)
- Replaced Long.valueOf().intValue() with direct int casting (3476df1)
- Removed public modifiers from JUnit test methods (422033d)

#### Dependency Updates
- Upgraded to Spring Boot 4.0.1 and Spring Framework 7.0.3 (a20a730, 87b9ae9)
- Updated JUnit versions for Cucumber 7.33.0 compatibility (f58132b)
- Bumped build-tools group with 5 updates (e66e3b1)
- Bumped testing group with 5 updates (a537571)
- Updated io.rest-assured:rest-assured to 6.0.0 (c95a582)
- Updated org.apache.tomcat:tomcat-juli to 11.0.15 (9ec6741)
- Updated com.google.guava:guava to 33.5.0-jre (2c8056e)
- Updated org.hamcrest:hamcrest to 3.0 (bb9b18e)
- Updated com.jayway.jsonpath:json-path to 2.10.0 (651205c)
- Updated org.slf4j:slf4j-api to 2.0.17 (3860995)
- Updated org.awaitility:awaitility to 4.3.0 (b6f4c06)

### January 21, 2026

#### Java 25 Migration
- **feat**: Upgraded to Java 25 with full dependency compatibility (ed92f8b)
- Updated documentation with Java 25 release status and new features (37efdfa)
- Added git-code-format-maven-plugin as Spotless alternative (1d18821)

#### Java 21 Features Implementation
- Applied record patterns and sequenced collections (aa8cfbd, a81293d)
- Enabled virtual threads for web and async processing (1824ec3)
- Updated roadmap with completion status (63ba177, cf06141)

#### Records Migration
- Migrated state hierarchy to sealed classes with records (4bcd1c1)
- Migrated DTOs to Java records across multiple components:
  - Club: JoinRequest, Command classes, ClubView (b1c1414, 22958ae, 0241704)
  - Authorization: Command and View classes (c9d4214, 547fb92)
  - ClassManager: Command and View classes (babdce6, e8d7dbd)
  - Auction: StartAuctionCommand, AuctionView (1812b18, 7c6b706)
  - Room: CreateRoomCommand, RoomView (487b411, 4a85f0c)
  - Member: CreateMemberCommand, MemberView (ad794b6, 7d78f8f)
  - Building: CreateBuildingCommand, BuildingView (1716565, ebd0d8d)
- Fixed test failures from DTO to record migration (81cedfb)

### January 20, 2026
- Updated multiple components to Java records:
  - slotlifecycle (db25e99)
  - classmanager (a8d967f)
  - club (9854090)
  - building (3970e53)
  - room (cd0a30e)
  - auction (a8ceb99)
  - slot (59d54df)
  - booking (5a015d1)

### January 19, 2026
- Updated to Java 21 (fd8f0be)

### January 18, 2026
- Updated to Java 17 and Spring 3 (7c22672)

### January 16, 2026
- Migrated to Java 11 and updated required dependencies (6d18765)

## Technology Stack
- **Java**: 25 (managed via mise)
- **Build Tool**: Maven (multi-module)
- **Framework**: Spring Boot 4.0.1, Spring Framework 7.0.3
- **Testing**: JUnit 5, Cucumber 7.33.0, Mockito, REST Assured 6.0.0
- **Code Quality**: Spotless (google-java-format 1.33.0), SonarCloud
- **CI/CD**: GitHub Actions
- **Main Branch**: master

## Key Features Implemented
- Virtual threads for improved concurrency
- Record patterns for cleaner data handling
- Sequenced collections API
- Sealed classes with records for type hierarchies
- Comprehensive DTO-to-record migration

---
