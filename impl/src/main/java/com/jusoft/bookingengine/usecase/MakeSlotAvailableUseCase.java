package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.slot.api.MakeSlotAvailableCommand;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MakeSlotAvailableUseCase {

  private final SlotManagerComponent slotManagerComponent;

  public void makeSlotAvailable(MakeSlotAvailableCommand command) {
    slotManagerComponent.makeAvailable(command.getSlotId());
  }
}
