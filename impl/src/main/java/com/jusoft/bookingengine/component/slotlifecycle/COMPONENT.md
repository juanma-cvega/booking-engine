# SlotLifecycle Component

Orchestrates what happens after a slot is created: decides whether it should go directly
to available, be pre-reserved by a class, or enter an auction. Acts as the lifecycle
coordinator between the slot, room, class, and auction components.

## Responsibility
This component does not own domain data — it reacts to events and delegates. Its core
job is to evaluate the `NextSlotState` and trigger the appropriate downstream action.

## NextSlotState sealed hierarchy
```
NextSlotState (sealed interface)
├── AvailableState    — slot becomes directly available
├── InAuctionState    — slot should be sent to auction
└── PreReservedState  — slot is pre-reserved by a class
```
The correct state is determined by querying the room's `SlotCreationConfigInfo` strategy
and checking whether any registered class timetables intersect with the slot's open time.

## Events consumed (via use cases triggered by listeners)
| Event (from) | Action |
|---|---|
| `SlotCreatedEvent` (slot) | Evaluates next state; triggers available/auction/class path |
| `AuctionWinnerFoundEvent` (auction) | Pre-reserves slot for winner |
| `AuctionUnsuccessfulEvent` (auction) | Makes slot available |
| `SlotMadeAvailableEvent` (slot) | Schedules slot opening via scheduler |

## Events published
| Event | When |
|---|---|
| `ClassReservationCreatedEvent` | Slot pre-reserved for a class |

## Dependencies
- `slot.api.SlotManagerComponent` — to reserve/pre-reserve/make-available
- `room.api.RoomManagerComponent` — to query next slot scheduling config
- `classmanager.api` — to check class timetable intersections
- `auction.api.AuctionManagerComponent` — to start auctions
- `scheduler.api.SchedulerComponent` — to schedule future events
- `authorization.api.AuthorizationManagerComponent` — to determine SlotStatus for auth checks
