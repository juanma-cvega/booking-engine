# Slot Component

Manages time slots within a room. A slot represents a bookable unit of time.

## Invariants
- A slot starts in `CREATED` state and may only transition forward: `CREATED → AVAILABLE → RESERVED` or `CREATED → PRE_RESERVED → RESERVED`.
- A slot cannot be reserved unless it is open (`openDate.startTime <= now`).
- A slot cannot be reserved directly from `PRE_RESERVED` state by a different user.
- `makeAvailable` is valid from any state (used to release pre-reservations).

## State machine
- `CreatedSlotState` — singleton; can `makeAvailable` or `preReserve` (if open)
- `AvailableSlotState` — singleton; can `reserve` or `preReserve` (if open)
- `PreReservedState` — instance (holds the reserving user); rejects different users
- `ReservedState` — instance (holds the reserved user); terminal for normal flow

The internal `SlotState` interface is package-private. The public `api.SlotState` enum is the view representation — `SlotStateFactory` maps between the two.

## Events published
| Event | When |
|---|---|
| `SlotCreatedEvent` | Slot created |
| `SlotReservedEvent` | Slot reserved by a user |
| `SlotPreReservedEvent` | Slot pre-reserved (by class or auction) |
| `SlotMadeAvailableEvent` | Slot released back to available |

## Repository concurrency contract
- All mutations of an existing slot go through `execute(id, UnaryOperator<Slot>)`, which performs its read-modify-write atomically under the component's single `ReentrantLock` (ADR-009, ADR-004).
- `save(Slot)` is **insert-only**: it stores a brand-new slot under the same lock and throws `SlotAlreadyExistsException` if the id already exists. Never use `save` to persist a mutation of an existing slot — use `execute`.
- Because `save` and `execute` share the same lock, they are mutually serialized: no create can interleave with and clobber an in-flight modification.
- The `ReentrantLock` and the backing `ConcurrentHashMap` are complementary, not redundant. The lock serializes the compound read-modify-write mutations (`execute`, and the guarded `save`); the `ConcurrentHashMap` keeps the lock-free query methods (`find`, `getLastCreatedFor`, `findSlotInUseOrToStartFor`, `findOpenSlotsByRoom`) safe against a concurrent write. Those queries do not take the lock, so the store must remain a `ConcurrentHashMap`.

## Dependencies
- `timer.OpenDate` — used in `CreateSlotCommand` and `SlotView`
- `MessagePublisher` — event bus

