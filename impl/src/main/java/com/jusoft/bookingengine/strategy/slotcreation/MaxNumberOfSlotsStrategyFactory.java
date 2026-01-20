package com.jusoft.bookingengine.strategy.slotcreation;

import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategy;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategyFactory;
import java.time.Clock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MaxNumberOfSlotsStrategyFactory
        implements SlotCreationStrategyFactory<MaxNumberOfSlotsStrategyConfigInfo> {

    private final SlotManagerComponent slotManagerComponent;
    private final Clock clock;

    @Override
    public SlotCreationStrategy createInstance(MaxNumberOfSlotsStrategyConfigInfo config) {
        return new MaxNumberOfSlotsStrategy(slotManagerComponent, clock, config);
    }
}
