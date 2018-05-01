package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotUser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotifyOfSlotReservationUseCase {

  private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  public void notifyOfSlotReservation(long slotId, SlotUser slotUser) {
    slotLifeCycleManagerComponent.notifyOfSlotReservation(slotId, slotUser);
  }
}
