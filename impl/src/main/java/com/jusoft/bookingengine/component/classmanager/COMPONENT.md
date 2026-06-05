# ClassManager Component

Manages recurring scheduled activities (classes) that pre-reserve slots in one or more
rooms on a recurring timetable. A class has instructors and is registered against specific
rooms and time windows.

## Invariants
- A class must have at least one instructor.
- A class cannot be removed while it is still registered in rooms
  (`ClassIsStillRegisteredInRoomsException`).
- Class timetables (via `ClassTimetable` and `ReservedSlotsOfDay`) define which slots the
  class claims. Two classes cannot overlap on the same slot.

## Events published
| Event | When |
|---|---|
| `ClassCreatedEvent` | Class created (carries `classId`, `description`, `instructorsId`, `classType`) |
| `ClassInstructorAddedEvent` | Instructor added to a class |
| `ClassInstructorRemovedEvent` | Instructor removed from a class |

## Events consumed (via use cases triggered by listeners)
| Event (from) | Action |
|---|---|
| `SlotCreatedEvent` (slot) | Checked by slotlifecycle to see if a class claims this slot |

## Dependencies
- `slotlifecycle.api.ClassTimetable` — used by slotlifecycle to check intersections
- `MessagePublisher` — event bus

## Notable types
- `ClassTimetable` — lives in `slotlifecycle.api` (not here), used cross-component.
  Represents a class's weekly time reservation as a list of `ReservedSlotsOfDay`.
- `ReservedSlotsOfDay` — a day-of-week + list of time windows.
