package com.jusoft.bookingengine.usecase.slot;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotUser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PreReserveSlotUseCase {

  private final SlotManagerComponent slotManagerComponent;

  public void preReserveSlot(long slotId, long userId, String userType) {
    slotManagerComponent.preReserveSlot(slotId, SlotUser.of(userId, userType));
  }
}
