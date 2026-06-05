# Club Component

Represents an organization. Clubs have admins and a membership process via join requests.

## Invariants
- A club name must be unique — `ClubRepositoryInMemory.save` enforces this inside the lock.
- Only admins may accept or deny join requests (`ClubAuthorizationException`).
- A club must have at least one admin at creation (enforced via `Validate.notEmpty(admins)`).
- A join request must exist in the club before it can be accepted or denied.

## Membership flow
1. User submits a join request → `JoinRequestCreatedEvent`
2. Admin accepts → `JoinRequestAcceptedEvent` (listener in `authorization` creates a `Member`)
3. Admin denies → `JoinRequestDeniedEvent`

## Events published
| Event | When |
|---|---|
| `ClubCreatedEvent` | Club created (triggers authorization component to create a Club shadow) |
| `JoinRequestCreatedEvent` | Join request submitted |
| `JoinRequestAcceptedEvent` | Join request accepted (carries `userId` and `clubId`) |
| `JoinRequestDeniedEvent` | Join request denied |

## Open design questions (existing TODOs)
- Should `JoinRequest` become its own bounded context / component?
- Should `Member` hold a role, removing the need for the `admins` set on `Club`?

## Dependencies
- `MessagePublisher` — event bus

