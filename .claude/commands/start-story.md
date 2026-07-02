# Start Story

Work a Taiga user story end to end: select it, start it, build it test-first, commit it, and close
it. Each step is a checkpoint — stop for review before proceeding.

This command owns the **sequence**, not the mechanism. It delegates board operations to
`/manage-backlog-item` and the test-first build to `/develop-use-case`.

---

## Step 1 — Select the story

Use `/manage-backlog-item`'s *Find stories* operation to retrieve candidates (the active sprint's
stories, or epics → stories if no sprint is active). Present them and let the user choose.

If no story fits the intended work, use `/manage-backlog-item`'s *Create a story* operation first —
every change must map to a story.

**Stop. Confirm the chosen story with the user before continuing.**

---

## Step 2 — Read and restate the acceptance criteria

Read the chosen story in full. Restate its goal and list its acceptance criteria as you understand
them — these are the source for the failing tests. Surface any ambiguity or missing case now.

**Stop. Confirm the acceptance criteria with the user before continuing.**

---

## Step 3 — Mark it in progress

Use `/manage-backlog-item` to move the story to *In Progress*, so the board reflects that the work
is active.

---

## Step 4 — Build it test-first

Drive the change through the red → green → refactor cycle, with the mandatory user checkpoint on
the tests (see CLAUDE.md):

- **New use case** → run `/develop-use-case`.
- **Bug fix or behaviour-changing refactor** → add or adjust a failing scenario that exposes the
  behaviour first.

The failing tests are written and validated by the user **before** any implementation.

---

## Step 5 — Commit

Commit with Conventional Commits, scoped to the component touched, carrying the story summary and
the `Taiga-US:` / `Taiga-URL:` trailers (see CLAUDE.md). One story may span several commits; every
commit that implements story work carries the trailers.

---

## Step 6 — Close the story

Once all the story's acceptance criteria are green, use `/manage-backlog-item` to update or close
the story with the commit / PR reference.
