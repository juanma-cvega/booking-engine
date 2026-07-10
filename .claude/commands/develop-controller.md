# Develop Controller

Front an existing use case with a REST controller, or an existing domain event with a
consumer/listener — following ADR-010 and the project's TDD workflow. Each step is a checkpoint —
stop and wait for review before proceeding to the next.

This command assumes the use case(s) it fronts already exist and are BDD-green (built via
`/develop-use-case`). It adds no business logic — a controller/listener translates a
request/event into a use case call and a result back into a response/published message, nothing
more (ADR-010).

---

## Step 0 — Confirm the use case(s) and read ADR-010

Identify the use case(s) this controller or listener will call. Read:
- `docs/design-decisions.md` — ADR-010 (controllers/listeners as thin adapters) and ADR-002 (use
  case layer).
- The `COMPONENT.md` of every component whose `api/` types (commands, views, events, exceptions)
  will cross this boundary.

Summarise: which use case method(s) this fronts, what request/event shape maps to which command,
what exceptions the use case can throw and what HTTP status / consumer behaviour each should map
to.

**Stop. Confirm this summary with the user before continuing.**

---

## Step 1 — Red: write the test first

This layer is **not** Cucumber — `impl/src/test/resources/features/` is reserved for the
use-case/domain layer. Controllers and listeners use plain JUnit 5 + Mockito, following
`BookingControllerRestTest`/the existing `listener/` classes as the template.

**For a REST controller:**
- `@ExtendWith(MockitoExtension.class)`, `@Mock` the use case class (**never** the component
  interface — that's the ADR-010 rule this whole command exists to protect), `@InjectMocks` the
  controller.
- `MockMvc.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler())`.
- Cover: the happy path (status + every response field via `jsonPath`), request validation
  failure (`@Valid` catches a bad field → 400), one scenario per exception the use case can throw
  asserting the specific status, and `verify(mockUseCase).method(exactArgs)` for calls with no
  return value.
- Any new exception type needs a new `@ExceptionHandler` method in `GlobalExceptionHandler` (never
  a per-controller try/catch) **and** a test proving that mapping.

**For a listener/consumer:**
- `@ExtendWith(MockitoExtension.class)`, `@Mock` the use case class, construct the listener
  directly, invoke its `@EventListener` method with a test message, `verify` the use case was
  called with the translated arguments.

Write the test now, together with the minimum scaffolding it needs to **compile**: the
request/response records in `controller/<name>/api/` (pure contracts, no logic) and the
controller/listener and factory classes as behaviourless stubs (bodies return null / do nothing).
No `@Configuration` wiring yet. **Red means the suite compiles and runs, failing on assertions** —
a suite that fails to compile is not yet an executable specification.

**Stop. Present the failing test and wait for approval before continuing.**

---

## Step 2 — Green: implement the adapter

Fill in the Step 1 stubs with the minimum behaviour to pass:
- The request/response records (or event-to-command mapping) in `controller/<name>/api/` stay a
  **separate** DTO layer from the component's own `component/<name>/api/` commands/views; never
  reuse one for the other.
- Implement the `XCommandFactory` (request/event → use case command) and, for controllers, the
  `XResourceFactory` (use case result → response).
- Implement the controller or listener class itself, depending only on the use case and the DTOs
  above.
- A `@Configuration` class (`XControllerConfig` / extend `MessageListenersConfig`) that
  `@Autowired`s the **use case bean** (already wired in `impl`'s own `usecase/<component>/
  *UseCaseConfig`) — never the component interface — and builds the adapter's `@Bean`s. Import it
  into `ControllerConfig`.

**Stop. Present the scaffolding and wait for approval before continuing.**

---

## Step 3 — Verify and refactor

Run, in the `controller` module:
- The new test, green.
- `ControllerArchitectureRulesTest` — confirms no dependency on a `*ManagerComponent` interface
  crept in, exception handling stayed centralized, and ADR-003's manual-DI rules held.
- `mvn spotless:apply` before presenting.

**Stop. Present the passing tests and the architecture check for final review.**
