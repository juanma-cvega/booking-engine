package com.jusoft.bookingengine.component.mock;

import com.jusoft.bookingengine.component.scheduler.ScheduledTask;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Getter
public class ScheduledTasksExecutor {

  private static final int NANOS_IN_MILLI = 1000;

  private final List<ScheduledTask> scheduledTasks;
  private final MessagePublisher messagePublisher;

  public void executeLateTasks(ZonedDateTime current, ZonedDateTime previous) {
    scheduledTasks.forEach(scheduledTask -> {
      long delayInSeconds = getDelay(scheduledTask.getScheduledEvent().getExecutionTime(), previous);
      if (isCurrentTimePassedExpectedExecutionTime(current, previous, delayInSeconds) && !scheduledTask.getTask().isDone()) {
        try {
          scheduledTask.getTask().cancel(true);
          messagePublisher.publish(scheduledTask.getScheduledEvent().getMessage());
          log.info("Scheduled task cancelled and triggered manually: {}", scheduledTask.getScheduledEvent());
        } catch (Exception e) {
          log.error("Unable to executeLateTasks task", e);
        }
      }
    });
  }

  private long getDelay(ZonedDateTime executionTime, ZonedDateTime current) {
    return Math.abs(ChronoUnit.SECONDS.between(current, executionTime));
  }

  private boolean isCurrentTimePassedExpectedExecutionTime(ZonedDateTime current, ZonedDateTime previous, long delay) {
    return previous.plusSeconds(delay).isEqual(current) || previous.plusSeconds(delay).isBefore(current);
  }
}
