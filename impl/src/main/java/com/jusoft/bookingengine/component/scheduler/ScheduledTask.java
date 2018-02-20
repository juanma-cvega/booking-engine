package com.jusoft.bookingengine.component.scheduler;

import com.jusoft.bookingengine.publisher.Message;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.concurrent.ScheduledFuture;

@Data(staticConstructor = "of")
public class ScheduledTask {

  private final ScheduledFuture<?> task;
  private final Message scheduledEvent;
  private final ZonedDateTime executionTime;
}
