package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class SlotPreReservedEvent implements Event {

  private final long userId;
  private final long slotId;
}
