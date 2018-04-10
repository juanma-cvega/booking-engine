package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotValidationInfo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReplaceSlotValidationUseCase {

  private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  public void replaceSlotValidation(long roomId, SlotValidationInfo slotValidationInfo) {
    slotLifeCycleManagerComponent.replaceSlotValidationWith(roomId, slotValidationInfo);
  }
}
