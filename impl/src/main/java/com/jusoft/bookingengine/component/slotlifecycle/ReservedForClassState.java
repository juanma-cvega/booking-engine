package com.jusoft.bookingengine.component.slotlifecycle;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = false)
class ReservedForClassState implements NextSlotState {

  private final long slotId;
  private final long classId;
}
