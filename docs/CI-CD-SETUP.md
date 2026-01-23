# CI/CD Setup Documentation

## Overview

This project uses GitHub Actions for continuous integration and deployment, with automated dependency management via Dependabot, code coverage tracking with JaCoCo, and code quality analysis with SonarQube.

---

## 🔄 Continuous Integration

### GitHub Actions Workflow

**Location**: `.github/workflows/ci.yml`

The CI pipeline runs on:
- Push to `master`, `main`, or `develop` branches
- Pull requests targeting these branches

### Pipeline Jobs

#### 1. **Build and Test**
- Checks out code with full history for SonarQube analysis
- Sets up Java 25 (Temurin distribution)
- Caches Maven dependencies for faster builds
- Compiles the project
- Runs all tests with code coverage
- Generates JaCoCo coverage reports
- Uploads test results and coverage reports as artifacts
- Publishes test reports in GitHub UI
- Runs SonarQube analysis (on push or internal PRs)
- Checks coverage thresholds

#### 2. **Code Quality Checks**
- Downloads coverage reports from previous job
- Generates coverage badges (optional)
- Additional quality gates

#### 3. **Dependency Security Check**
- Analyzes dependency tree
- Checks for available dependency updates
- Identifies potential security vulnerabilities

---

## 📊 Code Coverage

### JaCoCo Configuration

**Coverage Thresholds**:
- **Line Coverage**: Minimum 70%
- **Branch Coverage**: Minimum 60%

**Exclusions**:
- Configuration classes (`**/*Config.*`)
- API/DTO classes (`**/api/**/*`)

### Running Coverage Locally

```bash
# Run tests with coverage
mvn clean verify -Dspotless.check.skip=true

# Generate HTML report
mvn jacoco:report

# View report
open impl/target/site/jacoco/index.html
open controller/target/site/jacoco/index.html

# Check coverage thresholds
mvn jacoco:check
```

### Coverage Reports Location
- HTML Reports: `target/site/jacoco/`
- XML Reports: `target/site/jacoco/jacoco.xml`
- Execution Data: `target/jacoco.exec`

---

## 🔍 SonarQube Integration

### Configuration

**Project Key**: `booking-engine`

**Required Secrets** (set in GitHub repository settings):
- `SONAR_TOKEN`: Authentication token for SonarQube
- `SONAR_HOST_URL`: SonarQube server URL (e.g., `https://sonarcloud.io`)

### SonarQube Properties

Configuration file: `sonar-project.properties`

**Key Settings**:
- Java 25 source/target
- Coverage via JaCoCo XML reports
- JUnit test reports integration
- Exclusions for generated code and configs

### Running SonarQube Locally

```bash
# Run analysis (requires SONAR_TOKEN and SONAR_HOST_URL)
mvn sonar:sonar \
  -Dsonar.projectKey=booking-engine \
  -Dsonar.host.url=$SONAR_HOST_URL \
  -Dsonar.token=$SONAR_TOKEN \
  -Dspotless.check.skip=true
```

### What Gets Analyzed
- ✅ Code quality and maintainability
- ✅ Security vulnerabilities
- ✅ Code smells and technical debt
- ✅ Test coverage metrics
- ✅ Code duplications
- ✅ Complexity metrics

---

## 🤖 Dependabot

### Configuration

**Location**: `.github/dependabot.yml`

**Update Schedule**: Weekly on Mondays at 09:00

### Dependency Groups

1. **Spring Framework**
   - All `org.springframework*` packages
   - Minor and patch updates

2. **Testing Libraries**
   - JUnit, Mockito, AssertJ, Cucumber
   - Minor and patch updates

3. **Build Tools**
   - Maven plugins, Spotless
   - Minor and patch updates

### Dependabot Behavior
- Maximum 10 open PRs for Maven dependencies
- Maximum 5 open PRs for GitHub Actions
- Auto-labels PRs with `dependencies` tag
- Commit messages prefixed with `chore(deps)` or `chore(ci)`

---

## 🚀 Setting Up CI/CD

### 1. GitHub Repository Setup

#### Required Secrets
Add these in **Settings → Secrets and variables → Actions**:

```
SONAR_TOKEN=<your-sonarqube-token>
SONAR_HOST_URL=<your-sonarqube-url>
```

#### Optional: SonarCloud Setup
If using SonarCloud:
1. Go to https://sonarcloud.io
2. Import your GitHub repository
3. Generate a token
4. Add token to GitHub secrets

### 2. Enable Dependabot

Dependabot is automatically enabled when `.github/dependabot.yml` exists.

**To review PRs**:
- Go to **Pull requests** tab
- Filter by label: `dependencies`
- Review and merge updates

### 3. Branch Protection Rules

Recommended settings for `master`/`main`:

- ✅ Require pull request reviews
- ✅ Require status checks to pass:
  - `Build and Test`
  - `Code Quality Checks`
  - `Dependency Security Check`
- ✅ Require branches to be up to date
- ✅ Require conversation resolution
- ✅ Include administrators

---

## 📈 Monitoring and Badges

### GitHub Actions Status

View workflow runs:
```
https://github.com/<owner>/booking-engine/actions
```

### Coverage Badge (Optional)

Add to README.md:
```markdown
![Coverage](https://img.shields.io/badge/coverage-XX%25-brightgreen)
```

### SonarQube Badge

Add to README.md:
```markdown
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=booking-engine&metric=alert_status)](https://sonarcloud.io/dashboard?id=booking-engine)
```

---

## 🔧 Troubleshooting

### Build Failures

**Spotless formatting issues**:
```bash
# Skip Spotless checks (temporary workaround for Java 25)
mvn clean verify -Dspotless.check.skip=true
```

**Coverage threshold failures**:
```bash
# Check current coverage
mvn jacoco:report
# Adjust thresholds in pom.xml if needed
```

### SonarQube Issues

**Authentication failures**:
- Verify `SONAR_TOKEN` is valid
- Check `SONAR_HOST_URL` is correct
- Ensure token has analysis permissions

**Missing coverage data**:
- Verify JaCoCo reports are generated: `target/site/jacoco/jacoco.xml`
- Check `sonar.coverage.jacoco.xmlReportPaths` in `sonar-project.properties`

### Dependabot Issues

**Too many PRs**:
- Adjust `open-pull-requests-limit` in `.github/dependabot.yml`
- Use dependency groups to batch updates

**Failed updates**:
- Check compatibility with Java 25
- Review PR for breaking changes
- Test locally before merging

---

## 📝 Best Practices

### Before Pushing Code

```bash
# 1. Run tests locally
mvn clean verify -Dspotless.check.skip=true

# 2. Check coverage
mvn jacoco:report
open target/site/jacoco/index.html

# 3. Run SonarQube locally (optional)
mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.token=$SONAR_TOKEN
```

### Reviewing Dependabot PRs

1. ✅ Check the changelog for breaking changes
2. ✅ Verify CI passes
3. ✅ Review dependency compatibility
4. ✅ Test critical paths if major update
5. ✅ Merge promptly to avoid conflicts

### Maintaining Coverage

- Write tests for new features
- Aim for >80% coverage on business logic
- Use `@ExcludeFromCodeCoverage` for generated code
- Review coverage reports in PRs

---

## 🎯 Quality Gates

### Automatic Checks

Every PR must pass:
- ✅ All tests (191 tests)
- ✅ Code coverage ≥70% (lines), ≥60% (branches)
- ✅ SonarQube quality gate
- ✅ No security vulnerabilities
- ✅ Dependency compatibility

### Manual Review

- Code review by team member
- Architecture review for significant changes
- Performance review for critical paths

---

## 📚 Additional Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Dependabot Documentation](https://docs.github.com/en/code-security/dependabot)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [SonarQube Documentation](https://docs.sonarqube.org/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)

---

## 🔄 Maintenance

### Weekly Tasks
- Review Dependabot PRs
- Check SonarQube dashboard for new issues
- Monitor test execution times

### Monthly Tasks
- Review coverage trends
- Update quality gates if needed
- Audit GitHub Actions usage

### Quarterly Tasks
- Review CI/CD pipeline efficiency
- Update documentation
- Evaluate new tools/practices
