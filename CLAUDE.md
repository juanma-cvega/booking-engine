# Booking Engine — Claude Instructions

## TDD Approach (mandatory for all code changes)

No implementation without a failing test first. This applies to every code change:
new features, bug fixes, TODO resolutions, and refactors that change behaviour.

The test form depends on the change:
- **New use case** — use the `/develop-use-case` skill, which guides the full sequence.
- **Bug fix or TODO** — add a scenario to an existing feature file, or modify step
  definitions to expose the broken behaviour. The test must fail before the fix is written.
- **Refactor that changes behaviour** — same as above: demonstrate the expected behaviour
  with a failing test, then implement.

The sequence is always the same: failing test → minimum implementation → green.
Never write implementation speculatively and fit tests to it afterwards.

---

## Architecture Rules

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
- Do not proceed past a checkpoint without explicit approval.
