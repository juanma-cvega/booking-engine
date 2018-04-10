package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassConfig;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddClassConfigUseCase {

  private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  public void addClassConfigTo(long roomId, ClassConfig classConfig) {
    slotLifeCycleManagerComponent.addClassConfigTo(roomId, classConfig);
  }
}
