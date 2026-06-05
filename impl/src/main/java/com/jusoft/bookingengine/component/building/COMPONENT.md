# Building Component

Represents a physical facility. Thin aggregate — holds an address and description.
Rooms belong to buildings.

## Invariants
- A building must exist before rooms can be added to it (enforced by the room component, not here).
- No update operations — buildings are effectively immutable after creation.

## Events published
| Event | When |
|---|---|
| `BuildingCreatedEvent` | Building created (carries `buildingId`, `address`, `description`) |

## What this component does NOT own
- The rooms inside the building — those are managed by the `room` component.
- Authorization configuration for the building — that lives in `authorization`.

## Dependencies
- `MessagePublisher` — event bus

