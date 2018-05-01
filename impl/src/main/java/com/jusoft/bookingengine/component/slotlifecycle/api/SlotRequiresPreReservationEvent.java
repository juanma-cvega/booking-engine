package com.jusoft.bookingengine.component.slotlifecycle.api;

import com.jusoft.bookingengine.publisher.Event;
import lombok.Data;

@Data(staticConstructor = "of")
public class SlotRequiresPreReservationEvent implements Event {

  private final long slotId;
  private final SlotUser slotUser;
}
