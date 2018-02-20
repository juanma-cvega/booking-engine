package com.jusoft.bookingengine.strategy.slotcreation;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategy;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;

@AllArgsConstructor
class MaxNumberOfSlotsStrategy implements SlotCreationStrategy {

  private final SlotManagerComponent slotManagerComponent;
  private final Clock clock;
  private final MaxNumberOfSlotsStrategyConfigInfo config;

  @Override
  public ZonedDateTime nextSlotCreationTimeFor(long roomId) {
    int numberOfOpenSlots = slotManagerComponent.findNumberOfSlotsOpenFor(roomId);
    ZonedDateTime creationTime;
    if (numberOfOpenSlots >= config.getMaxSlots()) {
      creationTime = slotManagerComponent.findSlotInUseOrToStartFor(roomId)
        .map(slotView -> slotView.getOpenDate().getEndTime())
        .orElseThrow(() -> new IllegalArgumentException("Unable to find next slot to finish")); //Shouldn't happen. There are slots open
    } else {
      creationTime = ZonedDateTime.now(clock);
    }
    return creationTime;
  }
}
