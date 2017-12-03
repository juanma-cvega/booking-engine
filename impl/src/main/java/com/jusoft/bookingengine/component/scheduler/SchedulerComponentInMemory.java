package com.jusoft.bookingengine.component.scheduler;

import com.jusoft.bookingengine.component.scheduler.api.ScheduledEvent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@AllArgsConstructor
class SchedulerComponentInMemory implements SchedulerComponent {

  private final Clock clock;
  private final MessagePublisher messagePublisher;
  private final Executor executor;
  private final List<ScheduledTask> scheduledTasks;

  @Override
  public void schedule(ScheduledEvent scheduledEvent) {
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture<?> schedule = scheduledExecutorService.schedule(createCommand(scheduledEvent), getDelay(scheduledEvent.getExecutionTime()), MILLISECONDS);
    log.info("Scheduled message: {}", scheduledEvent.getMessage());
    scheduledTasks.add(new ScheduledTask(schedule, scheduledEvent));
  }

  private Runnable createCommand(ScheduledEvent scheduledEvent) {
    //Executes the publishing in a different thread to avoid contention if the publisher calls the listener in the same thread
    return () -> executor.execute(() -> messagePublisher.publish(scheduledEvent.getMessage()));
  }

  private long getDelay(ZonedDateTime executionTime) {
    long delay = ChronoUnit.MILLIS.between(ZonedDateTime.now(clock), executionTime);
    return delay > 0 ? delay : 0;
  }
}
