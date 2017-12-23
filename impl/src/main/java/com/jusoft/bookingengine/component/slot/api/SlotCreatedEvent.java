package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
public class SlotCreatedEvent implements Event {

  private final long slotId;
  private final long roomId;
  @NonNull
  private final ZonedDateTime startDate;
  @NonNull
  private final ZonedDateTime endDate;
}
