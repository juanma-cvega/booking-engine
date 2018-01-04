package com.jusoft.bookingengine.strategy.slotcreation;

import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategy;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategyFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MaxNumberOfSlotsStrategyFactory implements SlotCreationStrategyFactory<MaxNumberOfSlotsStrategyConfigInfo> {

  private final SlotComponent slotComponent;
  private final Clock clock;

  @Override
  public SlotCreationStrategy createInstance(MaxNumberOfSlotsStrategyConfigInfo config) {
    return new MaxNumberOfSlotsStrategy(slotComponent, clock, config);
  }
}
