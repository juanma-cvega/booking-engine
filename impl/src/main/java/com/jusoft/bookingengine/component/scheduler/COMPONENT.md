# Scheduler Component

Schedules a `Message` to be published at a future `ZonedDateTime`. The published message
is picked up by whatever listener is registered for it, enabling time-triggered domain
events without polling.

## How it works
1. Caller passes an execution time and a `Message` to `schedule(...)`.
2. A `ScheduledExecutorService` fires a task at the computed delay (in milliseconds).
3. The task publishes the message via a virtual-thread executor to avoid blocking the
   scheduler thread and to prevent re-entrant listener calls on the same thread.

## Invariants
- If the computed delay is negative (execution time is in the past), the task fires
  immediately (delay clamped to 0).
- All scheduled tasks are tracked in a `CopyOnWriteArrayList` (for inspection/cancellation
  in future use cases — currently no cancellation API exists).

## Dependencies
- `MessagePublisher` — publishes the message when the timer fires
- `Clock` — to compute the delay from now to the execution time
- `Executors.newVirtualThreadPerTaskExecutor()` — each published event runs on its own
  virtual thread
- `Executors.newSingleThreadScheduledExecutor()` — the scheduling wheel

## Rules when working here
- Do not add domain logic. This component only knows about time and message delivery.
- New use cases that need time-triggered behaviour should publish a domain event and let
  a listener call `SchedulerComponent.schedule(...)` — do not call the scheduler directly
  from domain entities.
