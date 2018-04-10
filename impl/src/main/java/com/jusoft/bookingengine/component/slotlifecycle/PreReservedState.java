package com.jusoft.bookingengine.component.slotlifecycle;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = false)
class PreReservedState extends NextSlotState {

  private final long slotId;
  private final long userId;
}
