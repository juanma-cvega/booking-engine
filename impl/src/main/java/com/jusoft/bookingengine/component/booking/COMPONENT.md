# Booking Component

Records a confirmed reservation: a booking is the ledger entry proving a user holds a
slot. A booking is created in reaction to a slot being reserved — never directly by a
user request.

## Invariants
- Only the user who owns a booking may act on it.
- A booking is immutable after creation — no update operations exist.
- Booking time is stamped at creation from the injected clock.

## User flow
- **Creating is a reaction, not a command.** When a slot is reserved by a person, this
  component reacts by creating the booking for that slot and the user who reserved it.
  The reaction performs no checks of its own: the guarded command that produced the
  event already validated membership, authorization and slot availability. A
  reservation made by a class does not produce a personal booking.
- **Cancelling is the user-facing command.** Its guards live here, with the contested
  state: only the owner may cancel, and not once the slot has started.
- **Reading** is a user-facing query over the ledger, restricted to the owner.

## What this component does NOT own
- Slot state — the slot component owns it.
- Whether a user may take a slot — decided at reservation time. The booking-creation
  reaction must not re-check authorization.

## Events
- **Reacts to:** a slot reserved by a person — creates the booking for that slot and
  the user who reserved it.
- **Publishes:** booking created — announces the new booking, the user and the slot;
  booking canceled — announces the canceled booking and the slot it releases.
