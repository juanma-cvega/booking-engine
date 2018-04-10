package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveClassConfigUseCase {

  private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  public void removeClassConfigFrom(long roomId, long classId) {
    slotLifeCycleManagerComponent.removeClassConfigFrom(roomId, classId);
  }
}
