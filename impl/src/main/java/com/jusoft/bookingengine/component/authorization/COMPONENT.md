# Authorization Component

Implements domain-level access control: determines whether a member may perform an action
on a specific resource (building + room + slot), based on tags and policies stored in the
club. Models authorization as a Policy Decision Point similar to AWS IAM.

## Model
- **Club** — holds the policy: for each building → room → `SlotStatus`, a required set of `Tag`s.
- **Member** — holds the principal's credentials: for each building → room → `SlotStatus`, an assigned set of `Tag`s, plus an `auctionAccess` flag per room.
- **Authorization check** — succeeds if the member has at least one tag the club requires for that resource at that `SlotStatus` (OR semantics, not AND).
- **SlotStatus** — `EARLY_BIRD` (slot created within the configured window) or `NORMAL`. Determined at authorization time from `SlotAuthorizationConfig` on the room.

## Invariants
- A member must belong to the club before any authorization check (`UserNotMemberException`).
- Authorization for slot reservation requires tag match at building level AND room level.
- Authorization for auction bidding additionally requires the `auctionAccess` flag to be true.
- If the club has no tags configured for a resource, access is open to all members of that club.

## Events consumed (via use cases triggered by listeners)
| Event (from) | Use case triggered |
|---|---|
| `ClubCreatedEvent` (club) | `NewClubCreatedUseCase` — creates shadow Club in authorization |
| `JoinRequestAcceptedEvent` (club) | `NewMemberCreatedUseCase` — creates Member |

## Dependencies
- `member.api.UserNotMemberException` — imported cross-component (acceptable — exception only)
- `MessagePublisher` — not used directly; use cases are triggered by event listeners

