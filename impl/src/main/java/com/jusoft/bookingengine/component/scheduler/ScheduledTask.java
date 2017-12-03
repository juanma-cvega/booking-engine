package com.jusoft.bookingengine.component.scheduler;

import com.jusoft.bookingengine.component.scheduler.api.ScheduledEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.ScheduledFuture;

@Data
@AllArgsConstructor
public class ScheduledTask {

  private final ScheduledFuture<?> task;
  private final ScheduledEvent scheduledEvent;
}
