# Room Component

Represents a bookable space within a building. Owns the scheduling configuration that
determines when slots are created and how long they last.

## Invariants
- `openTimesPerDay` must be non-empty; each open time's duration must be an exact multiple
  of `slotDurationInMinutes` (enforced in constructor).
- `availableDays` must be non-empty.
- `openTimesPerDay` is sorted by `startTime` at construction — the order is load-bearing
  for the slot-finding logic; do not assume insertion order.
- A room belongs to exactly one building and one club.

## Slot scheduling logic (the most complex part of this component)
`Room.findFirstSlotDate(clock)` — computes the first slot that should open from now.
`Room.findNextSlotDate(lastSlotEndTime, clock)` — computes the next slot after a given end time.

Both methods navigate across open time windows and days, wrapping to the next day when
the current open period is exhausted. The `SystemLocalTime` type is timezone-aware and is
used throughout to avoid DST issues.

## Events published
| Event | When |
|---|---|
| `RoomCreatedEvent` | Room created |
| `SlotRequiredEvent` | Room signals that a new slot needs to be created |
| `SlotReadyEvent` | Room signals that a slot's open time has arrived |
| `AuctionRequiredEvent` | Room signals that a slot should go to auction |

## Dependencies
- `timer.OpenDate`, `timer.OpenTime`, `timer.SystemLocalTime` — scheduling types
- `strategy.slotcreation` — `SlotCreationConfigInfo` determines how slots are triggered
- `strategy.auctionwinner` — `AuctionConfigInfo` used in `AuctionRequiredEvent`
- `MessagePublisher` — event bus

