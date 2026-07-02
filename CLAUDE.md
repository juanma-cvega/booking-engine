# Booking Engine — Claude Instructions

## Source of Work — Taiga (mandatory)

All new work originates from a user story in the Taiga project **`booking-engine`**. Do not start
coding from an informal request; first find — or create — the story that authorises the work.

- **Every change maps to a user story** — a feature, bug fix, or behaviour-changing refactor alike.
- **Creating and updating stories goes through the `manage-backlog-item` skill**, never through
  direct backend calls. The skill is backend-agnostic and defaults to **Taiga** as its backend. If
  no suitable story exists, invoke it to author one *before* development starts.
- **The ticket tracks the work.** Use `manage-backlog-item` to move the story to *In Progress* when
  work starts, and to update or close it (with the commit / PR reference) once the work is done.

Reading and selecting stories uses the configured **Taiga MCP server** directly (there is no Taiga
CLI); its tools are not always pre-loaded — discover them at the start of a session with
`ToolSearch` (query `taiga`) and use them to list and read stories (`list_projects`,
`get_current_sprint`, `list_epics`, `list_user_stories`, `list_tasks`). If the backend is
unreachable, stop and say so; do not invent work.

### Selecting the story to work on
1. **If a sprint (milestone) is active** — list the stories in the active sprint and present them
   for selection.
2. **If no sprint is active** — list the epics first and select one, then list that epic's stories
   and select the one to work on.
3. Present the candidates and let the user choose; never silently pick. Once chosen, read the story
   in full (description + acceptance criteria) — those acceptance criteria are the source for the
   failing tests required by the TDD workflow below.

### Committing story work
Commits must carry the story's information and a machine-readable reference back to Taiga. Use
Conventional Commits with a body summarising the story and trailers identifying it:

```
feat(booking): allow a member to cancel a reservation

From Taiga US #42 "Cancel a booking": a member can cancel their own
booking while the slot is still open.

Taiga-US: #42
Taiga-URL: <story permalink from the MCP>
Co-authored-by: Claude <claude@anthropic.com>
```

- `#42` is the story's Taiga reference number; `Taiga-URL` is the story permalink returned by the
  MCP (do not hand-construct it — different instances use different hosts).
- Scope the subject to the component/area touched (`booking`, `slot`, `auction`, …).
- One story may span several commits; every commit that implements story work carries the trailers.

---

## TDD Approach (mandatory for all code changes)

No implementation without a failing test first. This applies to every code change:
new features, bug fixes, TODO resolutions, and refactors that change behaviour.

The test form depends on the change:
- **New use case** — use the `/develop-use-case` skill, which guides the full sequence.
- **Bug fix or TODO** — add a scenario to an existing feature file, or modify step
  definitions to expose the broken behaviour. The test must fail before the fix is written.
- **Refactor that changes behaviour** — same as above: demonstrate the expected behaviour
  with a failing test, then implement.

### The cycle — red → green → refactor, with a user checkpoint on the tests
1. **Red — write the tests first, then stop.** Turn the story's acceptance criteria into failing
   tests (feature scenarios / step definitions). Run them, show them failing, and present them —
   the tests are the first deliverable. Write **no** production code yet.
2. **Checkpoint — the user validates the tests.** The tests are the specification for the change;
   the user must confirm they capture the story before any implementation begins. Do not proceed
   without explicit approval.
3. **Green — minimum implementation.** Write the least code that makes the validated tests pass —
   nothing the tests do not demand (see Hard Constraints).
4. **Refactor — improve under green.** Tidy the code with the tests staying green, honouring the
   ADRs.

Never write implementation speculatively and fit tests to it afterwards.

---

## Architecture Rules

All development must comply with the Architecture Decision Records in `docs/design-decisions.md`
(ADR-001 … ADR-009). The sections below summarise them; that document is the source of truth. A
subset is enforced mechanically by the ArchUnit suite in `impl` (`ArchitectureRulesTest`) — keep it
green.

### Component structure (ADR-006)
Every component lives under `component/<name>/`:

```
component/<name>/
├── api/                                  ← public contract only
│   ├── <Name>ManagerComponent.java       ← interface
│   ├── <Action>Command.java              ← record
│   ├── <Name>View.java                   ← record
│   ├── <Name>Event.java                  ← record
│   └── <Name>Exception.java
├── <Entity>.java                         ← package-private
├── <Name>ManagerComponentImpl.java       ← package-private
├── <Name>ManagerComponentConfig.java     ← @Configuration
├── <Name>Repository.java                 ← package-private interface
└── <Name>RepositoryInMemory.java         ← package-private
```

- Domain entities, repositories, and implementations are **package-private**.
- `api/` contains **no business logic** — only contracts and exceptions.
- Never add public visibility to domain classes to work around access restrictions;
  redesign the boundary instead.

### Dependency injection (ADR-003)
- Never use `@ComponentScan`, `@Component`, `@Service`, or `@Repository`.
- All beans are declared explicitly in `@Configuration` classes using `@Bean` methods.
- New components must be added to the `@Import` chain of the enclosing configuration.
- If you cannot trace a dependency from a `@Configuration` class, it does not belong.

### Use case layer (ADR-002)
- Every externally triggered operation goes through a use case class.
- Use cases live in `usecase/<component>/`.
- Each use case is a class with a single public method.
- Use cases depend on component **interfaces** (`api/`), never on implementations.
- Controllers and listeners invoke use cases; they never call components directly.

### Functional repository pattern (ADR-009)
Repositories accept a transformation function, not a modified entity:

```java
// Correct
repository.execute(id, entity -> entity.doSomething(value), () -> new NotFoundException(id));

// Wrong — do not do this
Entity e = repository.find(id);
e.doSomething(value);
repository.save(e);
```

New repository methods follow the signature:
`void execute(long id, UnaryOperator<Entity> fn, Supplier<RuntimeException> notFound)`

### Domain entities
- Constructors are package-private: `@AllArgsConstructor(access = AccessLevel.PACKAGE)`.
- Use static factory methods (`of(...)`) as the only creation point.
- **`@Data` is prohibited on domain entities** — it generates public setters.
  Use `@Getter` explicitly, or no Lombok at all for mutation-sensitive fields.
- State transitions return new instances; entities are effectively immutable after
  construction.

### DTOs (commands, views, events)
- Must be Java **records**.
- Sealed interfaces + records for state hierarchies (see `SlotState` as reference).

### Events (ADR-007)
- Cross-component side effects happen via published domain events, never via direct calls
  from one component implementation into another.
- Events are published by the component that owns the state change.
- Listeners live in `controller/` and react to events by invoking use cases.

### In-memory repositories (ADR-004)
- Every `find` method returns a **defensive copy**.
- All mutations are wrapped in `LockingTemplate`.

---

## Linting and formatting

Spotless (Google Java Format) is enforced by the pre-commit hook. Run it before
presenting code at any checkpoint so formatting issues are caught before review, not at
commit time.

```bash
mvn spotless:check        # verify
mvn spotless:apply        # fix
```

Never present code for review that fails `spotless:check`.

---

## Hard Constraints

- Do not add fields, methods, or classes not exercised by the current failing test.
- Do not add error handling for scenarios that cannot happen given domain invariants.
- Do not use `@ComponentScan` or any Spring auto-detection.
- Do not make domain classes or repositories public to bypass encapsulation.
- Do not skip the use case layer (listeners/controllers → use cases → components).
- Do not write comments that explain *what* the code does — only *why* when the reason
  is non-obvious.
- Do not write production code before the story's tests exist, fail, and have been validated by
  the user.
- Do not proceed past a checkpoint without explicit approval.
