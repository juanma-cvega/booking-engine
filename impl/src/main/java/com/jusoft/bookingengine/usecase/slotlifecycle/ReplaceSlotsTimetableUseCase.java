package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotsTimetable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReplaceSlotsTimetableUseCase {

  private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

  public void replaceSlotsTimetable(long roomId, SlotsTimetable slotsTimetable) {
    slotLifeCycleManagerComponent.replaceSlotsTimetableWith(roomId, slotsTimetable);
  }
}
