package com.jusoft.bookingengine.component.slotlifecycle;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = false)
class AvailableState implements NextSlotState {

  private final long slotId;
}
