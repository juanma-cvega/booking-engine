package com.jusoft.component.slot.api;

import com.jusoft.component.shared.Event;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class SlotCreatedEvent implements Event {

  private final long slotId;
  private final long roomId;
  @NonNull
  private final ZonedDateTime startTime;
  @NonNull
  private final ZonedDateTime endTime;
}
