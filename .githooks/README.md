# Git Hooks

This directory contains version-controlled Git hooks for the project.

## Available Hooks

### pre-commit
Runs before each commit to:
1. **Spotless formatting check** - Ensures code follows Google Java Format standards
2. **Test execution** - Runs all tests to prevent committing broken code
3. **Auto-update context.md** - Automatically adds commit information to `.cascade/context.md`

## Setup

After cloning the repository, run:

```bash
./githooks/setup.sh
```

Or manually configure:

```bash
git config core.hooksPath .githooks
chmod +x .githooks/pre-commit
```

## How It Works

### Spotless Check (Step 1)
- Validates code formatting before commit
- Uses Java 25 (falls back to Java 21 if unavailable)
- Fails commit if formatting issues are found
- Run `mvn spotless:apply` to fix formatting

### Test Execution (Step 2)
- Runs all tests with `mvn test`
- Prevents committing broken code
- Fails commit if any tests fail
- Run `mvn test` to see detailed output

### Context.md Auto-Update (Step 3)
- Only runs if formatting and tests pass
- Captures staged files before commit
- Adds a "Recent Changes" entry with date and file list
- Automatically stages the updated context.md
- Helps maintain project history for AI assistants and team members

## Disabling Hooks

To temporarily skip hooks:
```bash
git commit --no-verify
```

To disable permanently:
```bash
git config --unset core.hooksPath
```
