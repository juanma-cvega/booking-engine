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

Configured in the parent `pom.xml`, in the `<properties>` block. Analysis runs through
SonarScanner for Maven (`mvn sonar:sonar`), which derives sources, tests, binaries, libraries,
encoding and the Java version from the reactor — only settings it cannot infer are declared.

**Key Settings**:
- Coverage via JaCoCo XML reports
- Exclusions for generated code and configs
- Rule ignores for `api/` records and Lombok-generated fields

Run it locally with `mise run sonar` (needs `SONAR_TOKEN` and a prior `mvn install`).

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

## 🤝 Dependabot Auto-Merge

**Location**: `.github/workflows/dependabot-auto-merge.yml`

Dependabot dependency-update PRs are analysed by Claude Code and merged automatically when
both CI **and** the analysis pass. Anything risky is left for manual review — nothing is
merged silently.

### How it is triggered

The workflow uses the **`workflow_run`** trigger on the **CI/CD Pipeline** workflow, not
`pull_request`. This is deliberate:

- Dependabot PRs run with a **read-only token and no repository secrets** on `pull_request`
  (GitHub's guard against secret exfiltration via a malicious dependency). That context can
  neither run the analysis (needs `CLAUDE_CODE_OAUTH_TOKEN`) nor merge.
- `workflow_run` fires **after** CI completes and runs in the **trusted default-branch
  context** with full secrets and a write-capable token. It also inherently gates on CI
  having passed — the workflow only proceeds when the pipeline's `conclusion` is `success`.

### The three gates

```
Dependabot opens PR ──▶ CI/CD Pipeline runs ──▶ completes
                                                    │ workflow_run
                                                    ▼
   guard ──────────▶ analyse ────────────▶ merge
   CI success +      Claude Code review     Squash-merge via a scoped
   Dependabot        (🟢/🔴 flagged PR      GitHub App that bypasses the
   author/actor +    comment, verdict       ruleset — only if PASS and the
   same-repo head    PASS/FAIL)             PR does not touch workflows
```

1. **guard** — proceeds only when the CI run succeeded, the author **and** triggering actor
   are `dependabot[bot]`, and the head is a same-repo (non-fork) `dependabot/*` branch. It
   re-verifies the PR author against the API as defence in depth.
2. **analyse** — runs the Claude Code CLI (`claude-sonnet-5`, restricted to the read-only
   `Read,Grep,Glob` tools) over the diff to flag deprecated/removed APIs, breaking changes,
   and impacted usages. It posts its findings as a PR comment and emits a `PASS`/`FAIL`
   verdict. Any error, timeout, or missing verdict is treated as **FAIL** (fail-safe: the PR
   is not merged).
3. **merge** — runs only when the verdict is `PASS` **and** the PR does not touch
   `.github/workflows/**`. It mints a short-lived token for a dedicated GitHub App that is a
   **bypass actor** on the `master` ruleset, and squash-merges with branch deletion. The
   merge uses `gh pr merge --admin`: because the PR's global state is `BLOCKED` by the Code
   Owner review rule, `gh` refuses a plain merge client-side, so `--admin` calls the merge
   endpoint directly where the App's bypass is honoured. No approving review is submitted —
   an App review does not count toward the Code Owner requirement; the ruleset bypass is what
   authorises the merge.

**Workflow-file PRs are excluded from auto-merge.** `github-actions`-ecosystem Dependabot PRs
modify `.github/workflows/*`, which an App cannot merge without `workflows` write — a
permission deliberately withheld so a leaked App key cannot rewrite the CI pipeline. Such PRs
are still fully analysed by Claude and the comment is flagged **⚠️ Manual merge required**; you
review and merge them by hand.

Every analysis comment starts with a big status flag — **🟢 PASS** or **🔴 FAIL** — followed
by whether the PR will be auto-merged, will need a manual merge (workflow files), or will not
be merged, and then the detailed rationale.

### Why a GitHub App merges (and not `GITHUB_TOKEN`)

`master` requires a Code Owner review (see `.github/CODEOWNERS`), which the default
`GITHUB_TOKEN` cannot satisfy. A dedicated **GitHub App** on the ruleset **bypass list**
provides the merge identity — least privilege (`contents` + `pull_requests` write only),
short-lived tokens, no personal PAT. The App is deliberately **not** granted `workflows`
write: GitHub refuses to let an App merge changes to `.github/workflows/*` without it, so
PRs that touch workflow files are analysed and flagged but never auto-merged (see below).
Withholding that permission keeps the App unable to rewrite the secret-bearing CI pipeline
even if its key leaks.

The App credentials live **only** in the `dependabot-merge` Actions **environment**, whose
deployment-branch policy allows the default branch (`master`) exclusively. Because
`workflow_run` jobs run in the default-branch context they can read them, but any workflow
added or altered in a PR runs from the PR ref and is **denied** the environment — so it
cannot borrow the App token to approve its own PR. The secret, not the workflow logic, is the
security boundary.

### Supply-chain hardening

Both `ci.yml` and this workflow are hardened against dependency worms (e.g. Shai-Hulud):

- **All actions are pinned by commit SHA** (with a `# vN` comment); Dependabot's
  `github-actions` ecosystem keeps the pins updated.
- **`step-security/harden-runner`** runs first in every job — `block` with a minimal
  egress allowlist on the GitHub-API-only jobs (`guard`, `merge`), and `audit` on the
  broad-egress jobs (CI builds, `analyse`) pending endpoint discovery. See "Promoting
  harden-runner to block" below.
- The **Claude Code CLI is pinned to an exact version** and installed in a step with **no
  secrets in scope**, so an install-time (`postinstall`) compromise cannot read a token; the
  token is only present when the CLI is actually invoked.

### Required setup

Add these in **Settings**:

| Item | Where | Notes |
|------|-------|-------|
| `CLAUDE_CODE_OAUTH_TOKEN` | Actions secret (repo) | Used by the `analyse` job. Set a spend limit and rotate periodically. |
| GitHub App | Developer settings → GitHub Apps | Permissions: **Contents** + **Pull requests** = Read and write only (**not** Workflows — see below). Install on this repo. |
| `MERGE_APP_ID`, `MERGE_APP_PRIVATE_KEY` | **`dependabot-merge` environment** secrets | The App's ID and full `.pem` private key. Environment deployment branches restricted to `master`. |
| Ruleset bypass | Settings → Rules → Rulesets (`master`) | Add the GitHub App to the **bypass list** (`bypass_mode: always`). The App ID must match the ruleset's Integration bypass entry and the `MERGE_APP_ID` secret. |

### Promoting harden-runner to `block`

`audit` **records** egress but does not block it. After the first CI run and the first
Dependabot analysis, open the run's **harden-runner insights**, copy the observed endpoints
into the job's `allowed-endpoints:`, and switch `egress-policy` to `block`. That is when the
exfiltration path actually closes.

### Opting a PR out

To stop auto-merge for a specific update, review it manually before CI finishes, or convert
the analysis to `FAIL` by requesting changes / closing. A `FAIL` verdict (or any analysis
error) always blocks the merge; the reasoning is visible in the PR comment.

### Operating the auto-merge

**A PR that needs human changes becomes a manual merge — automatically.** The `guard` job
requires *both* `workflow_run.actor` **and** `triggering_actor` to be `dependabot[bot]`. The
moment you push a commit to a `dependabot/*` branch to adapt the code, the next CI run's
triggering actor is **you**, so auto-merge disengages and the PR reverts to a normal manual
merge (you are the Code Owner). Dependabot also stops managing a branch once a human pushes to
it. `@dependabot rebase` / `@dependabot recreate` are *not* human pushes — Dependabot performs
them, so the branch stays auto-merge-eligible; only your own commits disengage it.

**Re-triggering / onboarding existing PRs.** `workflow_run` only fires for CI runs that
complete *after* this workflow is on `master`, so PRs opened earlier are not evaluated until a
fresh, Dependabot-authored CI run occurs. Note:

- **Re-running the CI workflow manually does not work** — on a re-run GitHub sets
  `triggering_actor` to the person who clicked re-run, so the `guard` intentionally skips it.
  This is the check that stops anyone forcing an auto-merge by re-running CI.
- **Use a Dependabot command instead**, which makes Dependabot itself produce the new run:
  **`@dependabot recreate`** (rebuilds the PR + branch, guaranteeing a fresh CI run) is the
  reliable choice for onboarding. `@dependabot rebase` no-ops when the branch is already up to
  date, so it may not trigger a run.

**Bulk onboarding (one-off).** After this workflow first lands on `master`, refresh the
already-open Dependabot PRs from your own machine — under *your* write identity, so no stored
credential is involved. A mise task wraps this:

```bash
mise run dependabot:rebase            # comments "@dependabot rebase" on every open Dependabot PR
mise run dependabot:rebase recreate   # use "recreate" to force a fresh CI run when a branch is already current
```

It resolves the repo from `gh`, requires you to be logged in (`gh auth login`), and only ever
comments — it holds no token of its own.

> **Why not a workflow for this?** Dependabot **ignores `@dependabot` commands authored by
> `github-actions[bot]`** (the default `GITHUB_TOKEN`), so a comment-posting workflow would
> need a stored PAT or GitHub App whose identity Dependabot accepts — reintroducing a
> high-value, exfiltration-worthy credential for what is a rare, one-off task. A scheduled
> version would also remove the last human touchpoint, creating a fully autonomous merge-to-
> `master` loop. Prefer the local `gh` loop above; it uses your existing identity and adds no
> secret and no new attack surface.

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
- Check `sonar.coverage.jacoco.xmlReportPaths` in the parent `pom.xml`

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
