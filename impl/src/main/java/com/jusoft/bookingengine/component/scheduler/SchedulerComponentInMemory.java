package com.jusoft.bookingengine.component.scheduler;

import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.publisher.Message;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executor;
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
  private final ScheduledExecutorService scheduledExecutorService;

  @Override
  public void schedule(ZonedDateTime executionTime, Message scheduledEvent) {
    ScheduledFuture<?> schedule = scheduledExecutorService.schedule(createCommand(scheduledEvent), getDelay(executionTime), MILLISECONDS);
    scheduledTasks.add(new ScheduledTask(schedule, scheduledEvent, executionTime));
  }

  private Runnable createCommand(Message scheduledEvent) {
    //Executes the publishing in a different thread to avoid contention if the publisher calls the listener in the same thread
    return () -> executor.execute(() -> messagePublisher.publish(scheduledEvent));
  }

  private long getDelay(ZonedDateTime executionTime) {
    long delay = ChronoUnit.MILLIS.between(ZonedDateTime.now(clock), executionTime);
    return delay > 0 ? delay : 0;
  }
}
