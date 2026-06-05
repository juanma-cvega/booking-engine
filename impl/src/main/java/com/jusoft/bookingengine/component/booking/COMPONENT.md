# Booking Component

Records a confirmed reservation: a user owns a booking that references a slot.

## Invariants
- Only the user who created a booking may cancel it (`WrongBookingUserException`).
- A booking is immutable after creation — no update operations exist.
- Booking time is stamped at creation using the system clock.

## What this component does NOT own
- Slot state transitions — those happen in the `slot` component triggered by events.
- Authorization checks — the caller (use case) is responsible for verifying the user is allowed to book before invoking this component.

## Events published
| Event | When |
|---|---|
| `BookingCreatedEvent` | Booking created (carries `bookingId`, `userId`, `slotId`) |
| `BookingCanceledEvent` | Booking canceled |

## Dependencies
- `MessagePublisher` — event bus
