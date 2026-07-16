# Start Story

Work a Taiga user story end to end: select it, plan it, start it, build it test-first, review it,
commit it, and close it. Each step is a checkpoint — stop for review before proceeding.

This command owns the **sequence**, not the mechanism. It delegates board operations to
`/manage-backlog-item` and all generative and review work to dedicated subagents, each on its own
model — the orchestrator only coordinates and holds the user checkpoints.

---

## Step 1 — Select the story

Use `/manage-backlog-item`'s *Find stories* operation to retrieve candidates (the active sprint's
stories, or epics → stories if no sprint is active). Present them and let the user choose.

If no story fits the intended work, use `/manage-backlog-item`'s *Create a story* operation first —
every change must map to a story.

**Stop. Confirm the chosen story with the user before continuing.**

---

## Step 2 — Read the acceptance criteria

Read the chosen story in full. Restate its goal and list its acceptance criteria as you understand
them — surface any ambiguity or missing case now.

**Stop. Confirm the acceptance criteria with the user before continuing.**

---

## Step 3 — Plan, then validate the plan

Run **`planner`** (Opus) with the story and its acceptance criteria. Scoped to the one module the
story targets (domain `impl`, or the relevant infrastructure module), it produces implementation
**guidelines — not code**: the component/area to work in, how the use case fits the codebase, the
security to enforce, the use-case flow mapped to the tests to write (reusing existing steps), and the
`COMPONENT.md` documentation to add.

**If the planner reports the story is too broad** (it spans more than one part of the system), stop
and hand it back to the user with the suggested split — do not proceed until the story is narrowed.

Otherwise, validate the plan **in parallel** — dispatch both in one message:

- `architecture-reviewer` — is the plan sound against the ADRs / CLAUDE.md?
- `security-reviewer` — does it enforce the right authorization / access checks?

Keep the approved plan available to every later step (write it to a working file the subagents can
read). It is the spec the tests, implementation, and reviews are all measured against.

**Stop. Present the plan and the validation findings. Wait for the user to approve or refine the
plan before any code.**

---

## Step 4 — Mark it in progress

Use `/manage-backlog-item` to move the story to *In Progress*, so the board reflects that the work
is active.

---

## Step 5 — Build it test-first

Drive the change through red → green → refactor (see CLAUDE.md), with a review gate at each point
where a mistake is cheapest to catch.

### 5a — Red: write and review the failing tests
Dispatch **`test-author`** with the plan and the acceptance criteria. It writes the failing tests
(feature scenarios + step definitions, reusing existing steps) plus the `api/` contracts they need to
compile — the suite compiles and fails, with no implementation logic.

Then run **`test-reviewer`** (pass it the plan and the acceptance criteria): do the tests cover every
criterion and the planned flow, follow the project's Cucumber structure, and reuse existing step
definitions instead of duplicating them?

> `/develop-use-case` remains available as a standalone, manually-driven alternative outside this
> orchestrated flow.

**Stop. Present the failing tests together with the `test-reviewer` findings, and wait for the user
to validate the tests before any implementation.**

### 5b — Green + refactor
Dispatch **`implementer`** with the approved plan and the validated failing tests. It writes the
minimum production code to make them pass, refactors under green, runs the build itself, and returns
a summary (not full logs). It stays within the module the plan scoped and **never alters the tests**.

---

## Step 6 — Parallel review, then the finding triage loop

With the tests green, validate the code from three angles. **Dispatch all three reviewers in a
single message so they run in parallel** — each against the **plan** and the acceptance criteria,
each on the model set in its own agent file (`.claude/agents/`):

- `semantics-reviewer` — does the code correctly implement the story and the planned flow?
- `architecture-reviewer` — does it comply with the ADRs / CLAUDE.md rules?
- `security-reviewer` — does it enforce the security the plan required, and is it free of new flaws?

Instruct each reviewer to trace every externally-supplied field from the boundary to its first
real use — **including into unchanged code** — a defect can be an absence in the diff (a missing
validation, a missing handler) whose blast radius lives outside it.

Each returns ranked findings against the current working-tree diff. Consolidate them (drop
duplicates, order by severity), then run every finding through the triage loop below.

### Finding triage loop (generic — used by any flow that reviews story work)

Findings can come from the Step 6 subagents, from the **GitHub Claude review workflow** that runs
on every opened PR (fetch its output with `gh api` — issue comments, review bodies, and inline
review comments; keep a per-PR list of handled comment ids so nothing is triaged twice), or from
any other reviewer. Every finding gets the same treatment:

- **Valid** — it reproduces as a failing test, is within the story's scope, and fixing it violates
  no ADR and needs no missing prerequisite. Fix it test-first: red test capturing the failure,
  minimal fix under green (Step 5b), module architecture test, `mvn spotless:apply`. If the
  story's commits are already pushed to a PR, add a **follow-up commit** carrying the story's
  Taiga trailers — never amend or force-push published history.
- **Invalid** — not reproducible, out of the story's scope (suggest a gap/backlog story instead),
  or fixable only by violating an ADR or the Hard Constraints (e.g. speculative error handling).
  Record the finding and the rejection rationale in the triage log; the code does not change.

After fixes, re-run the review — re-dispatch the subagents on the updated diff, or, for PR
findings, push and wait for the PR workflow's fresh review — until a pass yields **no new valid
findings**. Cap at **2 fix rounds**; anything still open goes to the user as an explicit open
question, never silently dropped.

**Stop. Present the triage log — findings fixed and findings rejected with their rationale — to
the user.** Proceed only once the loop is clean or the user rules on the leftovers.

---

## Step 7 — Update documentation

Apply the plan's `COMPONENT.md` additions for the component(s) touched, so the docs match the new
behaviour.

---

## Step 8 — Commit

Commit with Conventional Commits, scoped to the component touched, carrying the story summary and
the `Taiga-US:` / `Taiga-URL:` trailers (see CLAUDE.md). One story may span several commits; every
commit that implements story work carries the trailers.

---

## Step 9 — Close the story

Once all the story's acceptance criteria are green, use `/manage-backlog-item` to update or close
the story with the commit / PR reference.
