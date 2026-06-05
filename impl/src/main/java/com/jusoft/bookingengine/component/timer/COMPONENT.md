# Timer Component

Not a domain component — provides shared time-related types and the system `Clock` bean
used by all other components.

## Provided types
- `OpenDate` — a `[startTime, endTime]` window using `ZonedDateTime`. Used by slots and
  auctions to represent their active period.
- `OpenTime` — a `[startTime, endTime]` window using `SystemLocalTime`. Used by rooms to
  define daily opening hours.
- `SystemLocalTime` — timezone-aware wrapper around `LocalTime`. Normalises times to the
  system clock zone to avoid DST issues when comparing times from different sources.

## Provided beans
- `Clock` (via `TimerConfig`) — `Clock.systemUTC()`. Injected by all components that need
  the current time. Always inject `Clock`, never call `ZonedDateTime.now()` or
  `LocalTime.now()` directly.

## Rules when working in this package
- Do not add domain logic here — these are utilities and value types only.
- New time-related value types belong here if they are shared across multiple components.
- `SystemLocalTime` implements `Temporal` to allow use in standard Java time APIs — treat
  it as a `LocalTime` with zone-awareness, not as a full `ZonedDateTime`.
