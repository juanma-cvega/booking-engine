# Parallel Stories

Work several **independent** Taiga stories at once — one git worktree and one background worker per
story — and integrate them serially through pull requests into `master`. Built for batches of
same-shaped work (e.g. one controller per existing use case) where the only expected overlap is the
shared `@Configuration` wiring.

This command owns the **orchestration**: story selection, worktree lifecycle, checkpoint brokering,
board updates, PRs, and serial integration. Workers own the code. Board operations go through
`/manage-backlog-item`; the technical work inside each worktree follows the skill that matches the
story (`/develop-controller` for controller/listener stories, `/develop-story-e2e` for use-case /
domain stories). Workers skip the followed skill's own story-selection and board steps — the
orchestrator owns those — and the skill's stop points are brokered as described below.

Because workers run in the background, they cannot talk to the user. **The orchestrator brokers
every checkpoint**: workers run *to* a checkpoint, return, the orchestrator batches all stories'
checkpoints into one review, and resumes each worker via SendMessage with the user's verdict. The
CLAUDE.md contract still holds — no implementation before the user validates that story's tests.

---

## Step 0 — Preflight

- `master` is clean and up to date with `origin/master`; `gh auth status` succeeds.
- `.worktrees/` is in `.gitignore` (worktrees live inside the repo).
- Cap concurrency at **3 workers**; queue the rest (parallel Maven builds are heavy).

Stop and report if any check fails.

---

## Step 1 — Select the parallel set

Use `/manage-backlog-item`'s *Find stories* operation (active sprint, or epics → stories).
Identify the candidates that can run in parallel — same kind of work, each scoped to a different
component/use case — and present them. **The user picks the set; never silently pick.**

Read each chosen story in full. Flag any pair whose scopes overlap beyond shared configuration —
overlapping stories must run serially or be re-cut, not parallelised.

**Stop. Confirm the selected set with the user before continuing.**

---

## Step 2 — Create worktrees, mark in progress, launch workers

For each story:

1. `git worktree add .worktrees/us<ref>-<slug> -b us<ref>-<slug> master`
2. Move the story to *In Progress* via `/manage-backlog-item` (the orchestrator owns **all** board
   operations — workers never touch the board).
3. Spawn a background worker with everything it needs in the prompt:
   - the **absolute worktree path** — it works only there, never in the main checkout;
   - the story ref, permalink, description, and acceptance criteria;
   - which skill's technical steps to follow up to the red tests (`/develop-controller`
     Steps 0–1, or `/develop-story-e2e` Steps 2–5a);
   - the instruction to **stop after the red tests** — run them, capture the failure, and return a
     summary (use case fronted, test file, failure output). No production code, no commits, no
     pushes yet.

---

## Step 3 — Checkpoint 1: validate the red tests (batched)

As workers return, collect their red-test summaries. When the batch is in, present **one review per
story**: the failing test, what it covers, how it maps to the acceptance criteria.

**Stop. The user approves or comments on each story's tests before any implementation.**

Resume each worker via SendMessage:
- **Approved** → continue with the skill's remaining steps (`/develop-controller` Steps 2–3, or
  `/develop-story-e2e` Steps 5b–7):
  scaffold, go green, run the module's architecture test, `mvn spotless:apply`, then **commit** in
  the worktree with Conventional Commits and the story's `Taiga-US:` / `Taiga-URL:` trailers
  (CLAUDE.md). Return a final summary: files touched, test results, architecture check.
- **Comments** → relay the feedback; the worker revises the tests and returns to this checkpoint.

---

## Step 4 — Checkpoint 2: final review and PRs (batched)

When a worker finishes, review its worktree diff — dispatch `architecture-reviewer` and
`semantics-reviewer` in parallel, pointed at the worktree path, each with the story's acceptance
criteria. Consolidate findings.

**Stop. Present each story's result — diff summary, green tests, architecture check, review
findings — and wait for a per-story verdict.**

- **Approved** → push the branch, open a PR into `master` with `gh` (story summary + Taiga
  trailers in the body), and add the PR link to the story via `/manage-backlog-item`.
- **Rejected with feedback** → SendMessage the feedback to the same worker in the same worktree; it
  revises under green and returns to this checkpoint. The worktree is **kept** — it is deleted only
  on merge or explicit abandon.
- **Abandoned** → close the PR if one exists, remove the worktree, delete the branch, and move the
  story back to its previous state via `/manage-backlog-item`.

---

## Step 5 — Integrate serially

The shared `@Configuration` wiring (ADR-003) guarantees the PRs conflict with each other, so they
merge **one at a time**:

1. Merge the approved PR into `master` (`gh pr merge`).
2. Rebase every remaining worktree branch onto the new `master`, resolve the (expected, one-line)
   configuration conflicts, and re-run that worktree's tests to confirm it is still green. If a
   rebase changes anything non-trivial, send it back through Checkpoint 2.
3. Close the merged story via `/manage-backlog-item` with the commit / PR reference.
4. `git worktree remove` the merged story's worktree and delete its branch.

Repeat until every story is merged or abandoned.

---

## Step 6 — Wrap up

Confirm `.worktrees/` is empty, `master` contains every merged story, and the board matches
reality (all worked stories closed or returned). Report the final state: stories merged, PRs,
anything abandoned and why.
