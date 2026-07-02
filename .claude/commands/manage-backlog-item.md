# Manage Backlog Item

The single, backend-agnostic entry point for **finding, creating, and updating work items**
(user stories) on the project's board. Every board read and write goes through here so the
workflow is not coupled to a specific tracker.

**Default backend: Taiga.** Only the *Backend* section below knows that — the operations are
written against generic board actions, so swapping trackers means editing one section, not the
workflow.

---

## Backend — Taiga (default)

Discover the tools with `ToolSearch` (query `taiga`), then resolve the project once:
`list_projects` → the one named **Booking engine** (slug `juanmacvega-booking-service`) → keep its
numeric `project_id`. When a sprint is active, scope `list_user_stories` to it (`sprint_id` from
`get_current_sprint`) so selection shows the current sprint's work.

The operations below map to these Taiga MCP tools:

| Action | Taiga MCP tool |
|--------|----------------|
| List sprint / epics | `get_current_sprint(project_id)`, `list_epics(project_id)` |
| List stories | `list_user_stories(project_id, sprint_id?, status?)` |
| Read one story | `get_story(story_id)` |
| Create a story | `create_story(project_id, subject, description, sprint_id?, epic_id?)` |
| Transition / update / close | `update_story(story_id, status="<board column name>", …)` |

- **`status` is the board column NAME, not an id** (e.g. `"In progress"`, `"Done"`) — it must match
  a status configured on the `booking-engine` board. Resolve the exact names from the project if
  unsure rather than guessing.
- After creating a story, take its **`ref`** (the per-project number cited in commit trailers) and
  URL from the response — do not hand-construct the permalink.
- If a required tool is not exposed, **stop and tell the user** — never edit the board by hand,
  invent a reference, or continue as if a write succeeded.

To use a different tracker, replace only this section with that tracker's tools; the operations
below stay the same.

---

## Operation — Find stories

Use when selecting which story to work on. This operation only **retrieves** candidates — the
caller (e.g. `/start-story`) presents them and the user chooses. Do not pick one yourself.

1. If a sprint is active (`get_current_sprint`), list that sprint's open stories
   (`list_user_stories` scoped to the sprint).
2. If no sprint is active, list the epics (`list_epics`); once the caller selects an epic, list
   that epic's stories.
3. Return each candidate's **reference, title, and status** — enough to present a choice.

---

## Operation — Create a story

Use when no existing story authorises the work (CLAUDE.md requires every change to map to a story).

### Step 1 — Draft the story
Gather the intent from the user, then write it well:

- **Title** — concise and action-oriented (e.g. "Cancel a booking").
- **Description** — `As a <role>, I want <capability>, so that <benefit>.`
- **Acceptance criteria** — a list of `Given / When / Then` scenarios covering the happy path and
  the key failure cases. Keep them concrete and testable: **they become the failing Cucumber
  scenarios in the TDD red step**, so vague criteria produce vague tests.
- **Notes** — affected component(s) and anything explicitly out of scope.

**Stop. Present the drafted story and wait for the user to approve or amend it before writing
anything to the backend.**

### Step 2 — Create it
On approval, create the story in the resolved backend under the `booking-engine` project (attach
it to the current sprint and/or epic when that context applies).

Report back the story's **reference number** and **permalink** — these are what commits cite
(`Taiga-US:` / `Taiga-URL:` trailers, per CLAUDE.md).

---

## Operation — Start work on a story

Use when implementation begins. Move the story to **In Progress** in the backend so the board
reflects that the work is active.

---

## Operation — Update / close a story

Use when the work is committed or merged. Add the commit or PR reference to the story and move it
to the appropriate review/done status. Do not close a story whose acceptance criteria are not all
green.

---

## Checkpoints

- A story is **always** presented to the user before it is written to the backend — never create
  one silently.
- If backend writes are unavailable, stop and surface it; never fabricate a story or a reference.
- Acceptance criteria are the contract handed to `/develop-use-case` (its Step 1 feature file) —
  write them as if someone will turn each one into a test, because they will.
