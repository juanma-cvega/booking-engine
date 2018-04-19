package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveClassTimetableUseCase {

  private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  public void removeClassTimetableFrom(long roomId, long classId) {
    slotLifeCycleManagerComponent.removeClassTimetableFrom(roomId, classId);
  }
}
