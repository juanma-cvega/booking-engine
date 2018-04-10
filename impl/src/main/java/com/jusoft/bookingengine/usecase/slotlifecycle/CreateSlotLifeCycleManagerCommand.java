package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotValidationInfo;
import lombok.Data;

@Data(staticConstructor = "of")
public class CreateSlotLifeCycleManagerCommand {

  private final long roomId;
  private final SlotValidationInfo slotValidationInfo;
}
