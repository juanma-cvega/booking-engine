package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class RemovePreReservationUseCase {

  private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  public void removePreReservationFrom(long roomId, ZonedDateTime slotStartTime) {
    slotLifeCycleManagerComponent.removePreReservation(roomId, slotStartTime);
  }
}
