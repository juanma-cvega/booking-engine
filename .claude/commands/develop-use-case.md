# Develop Use Case

Implement a new use case following the project's TDD workflow. Each step is a checkpoint
— stop and wait for review before proceeding to the next.

## Step 0 — Load component context

Before writing anything, identify which component(s) this use case touches.
For each component involved, read its `COMPONENT.md` file:

```
impl/src/main/java/com/jusoft/bookingengine/component/<name>/COMPONENT.md
```

Summarise what you learned: the component's invariants, known issues, and any open design
questions relevant to this use case. Surface anything that might affect the implementation
before a line of code is written.

**Stop. Confirm the component context with the user before continuing.**

---

## Step 1 — Feature file

Ask the user to describe the use case if not already specified.

Write the Cucumber `.feature` file in the `test/` module describing the use case in
Gherkin. Cover the happy path and the most important failure cases.

> Note: Cucumber is the current BDD mechanism. `docs/modernization-plan.md` section 3.5
> documents alternatives under evaluation (JUnit 5 nested tests, fluent DSL). If a
> replacement has been adopted, follow that approach instead.

**Stop. Present the feature file and wait for approval before continuing.**

---

## Step 2 — Step definitions and API contracts

Write the step definitions in `impl/src/test/` that exercise the use case end-to-end.
They must compile but the tests must fail — no implementation exists yet.

Create all `api/` contracts the use case requires:
- Commands (records)
- Views (records)
- Events (records)
- Exceptions
- New method signature on the component interface

Create the use case interface in `usecase/<component>/`.

**Stop. Present the new files and wait for approval before continuing.**

---

## Step 3 — Scaffolding

Implement the use case class — it delegates to the component interface, nothing more.

Add the method body to the component implementation class. It must compile but not
satisfy the domain logic yet (throw `UnsupportedOperationException` if needed — tests
must still fail at the domain level).

Wire the new beans in the relevant `@Configuration` class.

**Stop. Present the scaffolding and wait for approval before continuing.**

---

## Step 4 — Implementation

Write the domain logic that makes the failing tests pass.

Implement exactly what the tests require. No extra fields, methods, or classes beyond
what is needed to go green.
