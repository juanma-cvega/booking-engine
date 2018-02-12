package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.slot.api.MakeSlotAvailableCommand;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MakeSlotAvailableUseCase {

  private final SlotComponent slotComponent;

  public void makeSlotAvailable(MakeSlotAvailableCommand command) {
    slotComponent.makeAvailable(command.getSlotId());
  }
}
