# Member Component

Represents a user's membership in a club. Bridges the generic user identity (a bare `userId`)
with club-specific context.

## Invariants
- A member is always tied to exactly one club.
- A member is created in response to a join request being accepted — not directly.

## Events published
| Event | When |
|---|---|
| `MemberCreatedEvent` | Member created (carries `memberId`, `userId`, `clubId`) |

## Events consumed (via use cases triggered by listeners)
| Event (from) | Use case triggered |
|---|---|
| `JoinRequestAcceptedEvent` (club) | Creates the member |

## Open design questions (existing TODOs)
- `PersonalInfo` is explicitly set to `null` in `MemberFactory` — the design intends to
  hold name, surname, and date of birth, but it is unclear where this data comes from.
  Is it from a separate user profile service? This is unfinished.
- `MemberCreatedEvent` implements `Message` rather than `Event` — inconsistent with the
  rest of the event model. Likely an oversight.

