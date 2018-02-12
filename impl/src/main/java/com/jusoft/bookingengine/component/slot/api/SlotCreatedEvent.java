package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;
import lombok.NonNull;

@Data
public class SlotCreatedEvent implements Event {

  private final long slotId;
  private final long roomId;
  private final SlotState state;
  @NonNull
  private final OpenDate openDate;
}
