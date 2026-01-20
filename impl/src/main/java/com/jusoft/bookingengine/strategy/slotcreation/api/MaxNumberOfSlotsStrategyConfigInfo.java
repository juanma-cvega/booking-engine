package com.jusoft.bookingengine.strategy.slotcreation.api;

import lombok.Data;

@Data
public class MaxNumberOfSlotsStrategyConfigInfo implements SlotCreationConfigInfo {

    private final int maxSlots;
}
