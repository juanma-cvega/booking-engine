package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class FindNextSlotStateUseCase {

  private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  public void findNextSlotStateUseCase(long slotId, long roomId, ZonedDateTime slotStartTime) {
    slotLifeCycleManagerComponent.findNextSlotStateFor(slotId, roomId, slotStartTime);
  }
}
