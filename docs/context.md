# Booking Engine - Project Context

## Project Overview
A Java 25 booking engine project using Maven multi-module architecture, Spring Boot 4.0.1, and modern Java features.

## Recent Changes (Last 2 Weeks)

### Latest Commit: fix(ci): drop explicit checkout ref to clear Sonar S7631 on master
**Date:** Jul 19, 2026
**Commit:** d9d213d

**Changed files:**
- .github/workflows/dependabot-auto-merge.yml

---

### Latest Commit: fix(logging): use a logback encoder instead of a deprecated layout
**Date:** Jul 19, 2026
**Commit:** daa6dc9

**Changed files:**
- impl/src/main/resources/logback.xml
- test/src/test/resources/logback-test.xml

---

### Latest Commit: fix(ci): enforce HTTPS on tool downloads (Sonar S6506)
**Date:** Jul 18, 2026
**Commit:** 5473de6

**Changed files:**
- .github/workflows/ci.yml
- .github/workflows/dependabot-auto-merge.yml

---

### Latest Commit: fix: harden-runner requires to whitelist the url for the action lint binary
**Date:** Jul 18, 2026
**Commit:** 3095e79

**Changed files:**
- .github/workflows/ci.yml

---

### Latest Commit: fix(ci): import only each module's own JaCoCo report in Sonar analysis
**Date:** Jul 18, 2026
**Commit:** 81ae387

**Changed files:**
- pom.xml

---

### Latest Commit: fix(ci): harden the Dependabot auto-merge workflow against SonarCloud findings
**Date:** Jul 18, 2026
**Commit:** 0e0aa68

**Changed files:**
- .github/workflows/dependabot-auto-merge.yml

---

### Latest Commit: ci: lint GitHub Actions workflows with actionlint
**Date:** Jul 18, 2026
**Commit:** 37649dc

**Changed files:**
- .github/workflows/ci.yml
- .pre-commit-config.yaml

---

### Latest Commit: ci: gate the Claude PR review on the build and block secrets pre-commit
**Date:** Jul 17, 2026
**Commit:** d46e381

**Changed files:**
- .github/workflows/ci.yml
- .github/workflows/claude.yml
- .pre-commit-config.yaml
- CLAUDE.md
- docs/context.md

---

### Latest Commit: ci: gate the Claude PR review on the build and block secrets pre-commit
**Date:** Jul 17, 2026
**Commit:** 9abc43f

**Changed files:**
- .github/workflows/ci.yml
- .github/workflows/claude.yml
- .pre-commit-config.yaml
- CLAUDE.md

---

### Latest Commit: chore(agents): scope review subagents to what tooling cannot cover
**Date:** Jul 17, 2026
**Commit:** 53d48a1

**Changed files:**
- .claude/agents/architecture-reviewer.md
- .claude/agents/security-reviewer.md
- .claude/agents/semantics-reviewer.md

---

### Latest Commit: fix(ci): derive Sonar analysis classpath from the Maven reactor
**Date:** Jul 17, 2026
**Commit:** 6c23b8f

**Changed files:**
- .github/workflows/ci.yml
- docs/CI-CD-SETUP.md
- docs/context.md
- mise.toml
- pom.xml
- sonar-project.properties
- test/src/main/java/empty

---

### Latest Commit: fix(ci): derive Sonar analysis classpath from the Maven reactor
**Date:** Jul 17, 2026
**Commit:** 631ac3a

**Changed files:**
- .github/workflows/ci.yml
- docs/CI-CD-SETUP.md
- mise.toml
- pom.xml
- sonar-project.properties
- test/src/main/java/empty

---

### Latest Commit: ci: let the automatic PR review actually post its comment
**Date:** Jul 16, 2026
**Commit:** b4bf9c6

**Changed files:**
- .github/workflows/claude.yml
### Latest Commit: feat(room): expose room creation through a REST controller
**Date:** Jul 16, 2026
**Commit:** 93a110f

**Changed files:**
- controller/src/main/java/com/jusoft/bookingengine/controller/ControllerConfig.java
- controller/src/main/java/com/jusoft/bookingengine/controller/GlobalExceptionHandler.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/RoomCommandFactory.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/RoomControllerConfig.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/RoomControllerRest.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/RoomResourceFactory.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/api/CreateRoomRequest.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/api/OpenTimeRequest.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/api/OpenTimeResource.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/api/RoomResource.java

---

### Latest Commit: feat(room): expose room creation through a REST controller
**Date:** Jul 16, 2026
**Commit:** 1b4d90f

**Changed files:**
- controller/src/main/java/com/jusoft/bookingengine/controller/ControllerConfig.java
- controller/src/main/java/com/jusoft/bookingengine/controller/GlobalExceptionHandler.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/RoomCommandFactory.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/RoomControllerConfig.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/RoomControllerRest.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/RoomResourceFactory.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/api/CreateRoomRequest.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/api/OpenTimeRequest.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/api/OpenTimeResource.java
- controller/src/main/java/com/jusoft/bookingengine/controller/room/api/RoomResource.java

---

### Latest Commit: ci: review every PR change with Claude, codify the finding triage loop
**Date:** Jul 16, 2026
**Commit:** 3d2bc07

**Changed files:**
- .claude/commands/develop-story-e2e.md
- .claude/commands/parallel-stories.md
- .github/workflows/claude.yml

---

### Latest Commit: Add claude.yaml to allow Claude Code to review PRs
**Date:** Jul 16, 2026
**Commit:** cee9437

**Changed files:**
- .claude/commands/parallel-stories.md
- .claude/settings.json
- docs/context.md

---

### Latest Commit: feat(ci): add Claude PR review workflow (interactive @claude + review-on-open)
**Date:** Jul 16, 2026
**Commit:** 5cb669f

**Changed files:**
- .github/workflows/claude.yml
- docs/context.md

---

### Latest Commit: feat(ci): add Claude PR review workflow (interactive @claude + review-on-open)
**Date:** Jul 16, 2026
**Commit:** e65a974

**Changed files:**
- .github/workflows/claude.yml

---

### Latest Commit: feat(ci): switch harden-runner egress policy to block
**Date:** Jul 16, 2026
**Commit:** 3af66d0

**Changed files:**
- .github/workflows/ci.yml
- docs/context.md

---

### Latest Commit: feat(dependabot): switch analyse job egress policy to block
**Date:** Jul 16, 2026
**Commit:** 94d28e2

**Changed files:**
- .github/workflows/dependabot-auto-merge.yml
- docs/context.md

---

### Latest Commit: feat(dependabot): switch analyse job egress policy to block
**Date:** Jul 16, 2026
**Commit:** 62b8502

**Changed files:**
- .github/workflows/dependabot-auto-merge.yml
- docs/context.md

---

### Latest Commit: feat(dependabot): serialize auto-merge runs and skip PRs behind master
**Date:** Jul 16, 2026
**Commit:** 77a5f6b

**Changed files:**
- .github/workflows/dependabot-auto-merge.yml
- docs/context.md

---

### Latest Commit: feat(dependabot): serialize auto-merge runs and skip PRs behind master
**Date:** Jul 16, 2026
**Commit:** 8d0de9a

**Changed files:**
- .github/workflows/dependabot-auto-merge.yml
- docs/context.md

---

### Latest Commit: feat(dependabot): serialize auto-merges and group updates to avoid conflicts
**Date:** Jul 15, 2026
**Commit:** 690d44c

**Changed files:**
- .github/dependabot.yml
- .github/workflows/dependabot-auto-merge.yml
- docs/context.md

---

### Latest Commit: feat(dependabot): serialize auto-merges and group updates to avoid conflicts
**Date:** Jul 15, 2026
**Commit:** 3e7a855

**Changed files:**
- .github/dependabot.yml
- .github/workflows/dependabot-auto-merge.yml

---

### Latest Commit: fix(mise): make dependabot:rebase task parseable by mise's Tera engine
**Date:** Jul 15, 2026
**Commit:** f9e6403

**Changed files:**
- docs/context.md
- mise.toml

---

### Latest Commit: feat(dependabot): exclude workflow-file PRs from auto-merge, flag analysis
**Date:** Jul 15, 2026
**Commit:** 6f71507

**Changed files:**
- .github/workflows/dependabot-auto-merge.yml
- docs/CI-CD-SETUP.md
- docs/context.md

---

### Latest Commit: feat(dependabot): exclude workflow-file PRs from auto-merge, flag analysis
**Date:** Jul 15, 2026
**Commit:** 16b2ba0

**Changed files:**
- .github/workflows/dependabot-auto-merge.yml
- docs/CI-CD-SETUP.md

---

### Latest Commit: fix(dependabot): merge with --admin so the App bypass is honoured
**Date:** Jul 15, 2026
**Commit:** 4c99939

**Changed files:**
- .github/workflows/dependabot-auto-merge.yml
- docs/CI-CD-SETUP.md

---

### Latest Commit: docs(dependabot): document auto-merge flow and add rebase mise task
**Date:** Jul 13, 2026
**Commit:** 14e51b3

**Changed files:**
- docs/CI-CD-SETUP.md
- mise.toml

---

### Latest Commit: ci(dependabot): auto-analyse and merge Dependabot PRs, harden workflows
**Date:** Jul 13, 2026
**Commit:** 852f916

**Changed files:**
- .github/workflows/ci.yml
- .github/workflows/dependabot-auto-merge.yml

---

### Latest Commit: fix: remove sonar from maven configuration
**Date:** Jul 10, 2026
**Commit:** 92d27a0

**Changed files:**
- pom.xml

---

### Latest Commit: fix: sonar scanner configuration
**Date:** Jul 10, 2026
**Commit:** babfb73

**Changed files:**
- .gitignore
- mise.toml
- sonar-project.properties

---

### Latest Commit: fix: add empty file so that the folder test/src/main/java exists for sonar analysis
**Date:** Jul 10, 2026
**Commit:** 9d093d3

**Changed files:**
- test/src/main/java/empty

---

### Latest Commit: fix: use sonar GitHub action instead of the maven plugin to better use sonar-project.properties file
**Date:** Jul 10, 2026
**Commit:** 3529665

**Changed files:**
- .github/workflows/ci.yml
- mise.toml

---

### Latest Commit: fix: sonar maven goal not reading sonar-project.properties in the workflow
**Date:** Jul 10, 2026
**Commit:** 4426462

**Changed files:**
- .github/workflows/ci.yml

---

### Latest Commit: Use properties-maven-plugin to load the sonar-project.properties file
**Date:** Jul 10, 2026
**Commit:** 2c23f11

**Changed files:**
- .claude/commands/develop-controller.md
- .claude/commands/parallel-stories.md
- .gitignore
- pom.xml
- sonar-project.properties

---

### Latest Commit: Update sonar-project.properties exclusions configuration
**Date:** Jul 10, 2026
**Commit:** 9804452

**Changed files:**
- sonar-project.properties

---

### Latest Commit: feat(tooling): add parallel-stories orchestrator for concurrent story work
**Date:** Jul 09, 2026
**Commit:** 3055eb1

**Changed files:**
- .claude/commands/develop-story-e2e.md
- .claude/commands/parallel-stories.md
- .claude/commands/start-story.md
- .claude/settings.json
- .gitignore

---

### Latest Commit: build(tooling): migrate git hooks to the pre-commit framework
**Date:** Jul 08, 2026
**Commit:** 91b3992

**Changed files:**
- .cascade/context.md
- .claude/settings.json
- .githooks/README.md
- .githooks/post-commit
- .githooks/pre-commit
- .githooks/setup.sh
- .pre-commit-config.yaml
- docs/context.md
- mise.toml
- scripts/update-context-md.sh

---

### Latest Commit: fix(auction): anchor current-time step overrides to the domain clock
**Date:** Jul 08, 2026
**Commit:** 6206867

**Changed files:**
- impl/src/test/java/com/jusoft/bookingengine/usecase/SharedStepDefinitions.java

---

### Latest Commit: docs(architecture): add ADR-010 and enforce it for the controller module
**Date:** Jul 08, 2026
**Commit:** daf3696

**Changed files:**
- .claude/commands/develop-controller.md
- controller/pom.xml
- controller/src/test/java/com/jusoft/bookingengine/architecture/ControllerArchitectureRulesTest.java
- docs/design-decisions.md

---

### Latest Commit: docs(architecture): add ADR-010 and enforce it for the controller module
**Date:** Jul 08, 2026
**Commit:** 6b98026

**Changed files:**
- .claude/commands/develop-controller.md
- controller/pom.xml
- controller/src/test/java/com/jusoft/bookingengine/architecture/ControllerArchitectureRulesTest.java
- docs/design-decisions.md

---

### Latest Commit: fix(slot): guard slot repository save under the same lock as execute
**Date:** Jul 03, 2026
**Commit:** e55f1c3

**Changed files:**
- impl/src/main/java/com/jusoft/bookingengine/component/slot/COMPONENT.md
- impl/src/main/java/com/jusoft/bookingengine/component/slot/SlotRepositoryInMemory.java
- impl/src/main/java/com/jusoft/bookingengine/component/slot/api/SlotAlreadyExistsException.java
- impl/src/test/java/com/jusoft/bookingengine/component/slot/SlotRepositoryInMemoryTest.java

---

### Latest Commit: feat: add subagent pipeline to the start-story orchestrator
**Date:** Jul 03, 2026
**Commit:** f44c5bd

**Changed files:**
- .claude/agents/architecture-reviewer.md
- .claude/agents/implementer.md
- .claude/agents/planner.md
- .claude/agents/security-reviewer.md
- .claude/agents/semantics-reviewer.md
- .claude/agents/test-author.md
- .claude/agents/test-reviewer.md
- .claude/commands/start-story.md

---

### Latest Commit: feat: add Taiga story-workflow commands (manage-backlog-item, start-story)
**Date:** Jul 02, 2026
**Commit:** 0fb8766

**Changed files:**
- .claude/commands/manage-backlog-item.md
- .claude/commands/start-story.md
- CLAUDE.md

---

### Latest Commit: docs: require Taiga-sourced stories, TDD test-validation checkpoint, and ADR compliance
**Date:** Jul 02, 2026
**Commit:** 9118123

**Changed files:**
- CLAUDE.md

---

### Latest Commit: feat: Add ArchUnit library to create a test that enforces ADRs
**Date:** Jul 01, 2026
**Commit:** 1ac2f58

**Changed files:**
- impl/pom.xml
- impl/src/main/java/com/jusoft/bookingengine/component/authorization/AuthorizationManagerComponentImpl.java
- impl/src/main/java/com/jusoft/bookingengine/component/authorization/ClubRepositoryInMemory.java
- impl/src/main/java/com/jusoft/bookingengine/component/authorization/MemberRepositoryInMemory.java
- impl/src/main/java/com/jusoft/bookingengine/component/building/BuildingRepositoryInMemory.java
- impl/src/main/java/com/jusoft/bookingengine/component/club/ClubRepositoryInMemory.java
- impl/src/main/java/com/jusoft/bookingengine/component/member/MemberManagerComponentImpl.java
- impl/src/main/java/com/jusoft/bookingengine/component/slotlifecycle/SlotLifeCycleManagerRepositoryInMemory.java
- impl/src/main/java/com/jusoft/bookingengine/usecase/classmanager/RegisterRoomUseCase.java
- impl/src/main/java/com/jusoft/bookingengine/usecase/classmanager/UnregisterRoomUseCase.java

---

### Latest Commit: feat:Add Claude.md and COMPONENT.md to every component
**Date:** Jun 05, 2026
**Commit:** fa446c4

**Changed files:**
- .claude/commands/develop-use-case.md
- .gitignore
- CLAUDE.md
- impl/src/main/java/com/jusoft/bookingengine/component/auction/COMPONENT.md
- impl/src/main/java/com/jusoft/bookingengine/component/authorization/COMPONENT.md
- impl/src/main/java/com/jusoft/bookingengine/component/booking/COMPONENT.md
- impl/src/main/java/com/jusoft/bookingengine/component/building/COMPONENT.md
- impl/src/main/java/com/jusoft/bookingengine/component/classmanager/COMPONENT.md
- impl/src/main/java/com/jusoft/bookingengine/component/club/COMPONENT.md
- impl/src/main/java/com/jusoft/bookingengine/component/member/COMPONENT.md

---

### Latest Commit: Update cucumber
**Date:** Feb 10, 2026
**Commit:** c465af9

**Changed files:**
- impl/src/main/java/com/jusoft/bookingengine/component/authorization/Club.java
- pom.xml

---

### Latest Commit: Update hibernate validator version
**Date:** Feb 10, 2026
**Commit:** aee406e

**Changed files:**
- pom.xml

---

### Latest Commit: Install packages while testing in the CI for the sonar goal to see them
**Date:** Feb 10, 2026
**Commit:** 8f72ee3

**Changed files:**
- .github/workflows/ci.yml

---

### Latest Commit: Add CODEOWNERS file
**Date:** Feb 10, 2026
**Commit:** cd74aba

**Changed files:**
- .github/CODEOWNERS

---

### Latest Commit: Use new hibernate-validator group ID
**Date:** Feb 10, 2026
**Commit:** 7654163

**Changed files:**
- controller/pom.xml
- pom.xml

---

### Latest Commit: Add Sonar properties to sonar-project.properties file
**Date:** Feb 10, 2026
**Commit:** 6d335c5

**Changed files:**
- sonar-project.properties

---

### Latest Commit: ci: use SONAR_TOKEN env var instead of command line expansion
**Date:** Jan 30, 2026
**Commit:** 6fbbabf

**Changed files:**
- .github/workflows/ci.yml

---

### Latest Commit: Use fixed version of test reporter action
**Date:** Jan 30, 2026
**Commit:** e97c9d4

**Changed files:**
- .github/workflows/ci.yml

---

### Latest Commit: Remove unused parameter
**Date:** Jan 27, 2026
**Commit:** 0b50e81

**Changed files:**
- controller/pom.xml

---

### Latest Commit: fix: downgrade Hibernate Validator to 8.0.2 and add missing dependencies
**Date:** Jan 27, 2026
**Commit:** 5af4768

**Changed files:**
- pom.xml

---

### Latest Commit: ci: add sonar.java.binaries property to fix SonarQube caching warning
**Date:** Jan 27, 2026
**Commit:** 8c32ca4

**Changed files:**
- sonar-project.properties

---

### Latest Commit: test: fix cucumber.features property warning in UseCaseCTest
**Date:** Jan 27, 2026
**Commit:** 0385af2

**Changed files:**
- impl/src/test/java/com/jusoft/bookingengine/UseCaseCTest.java

---

### Latest Commit: test: fix Cucumber test discovery warnings
**Date:** Jan 27, 2026
**Commit:** 940616c

**Changed files:**
- impl/src/test/java/com/jusoft/bookingengine/CucumberTest.java

---

### Latest Commit: ci: remove dependency download step that fails on multi-module projects
**Date:** Jan 27, 2026
**Commit:** ed104ec

**Changed files:**
- .github/workflows/ci.yml

---

### Latest Commit: fix: move context.md update to post-commit hook for accurate commit messages
**Date:** Jan 24, 2026
**Commit:** 9a934e1

**Changed files:**
- .githooks/post-commit
- .githooks/pre-commit

---

### Latest Commit: Fix sonarqube organization name
**Date:** Jan 24, 2026

**Staged files:**
- .github/workflows/ci.yml
- .mvn/maven.config
- pom.xml
- sonar-project.properties

---

### Latest Commit: docs: update context.md with comprehensive git history from last 2 weeks
**Date:** Jan 24, 2026

**Staged files:**
- .github/workflows/ci.yml

---

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
