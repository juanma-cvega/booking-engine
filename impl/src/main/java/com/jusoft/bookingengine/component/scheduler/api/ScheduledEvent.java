package com.jusoft.bookingengine.component.scheduler.api;

import com.jusoft.bookingengine.component.shared.Message;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class ScheduledEvent implements Message {

  @NonNull
  private final Message message;
  @NonNull
  private final ZonedDateTime executionTime;
}
