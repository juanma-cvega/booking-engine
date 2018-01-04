package com.jusoft.bookingengine.strategy.slotcreation.api;

public interface SlotCreationStrategyFactory<T extends SlotCreationConfigInfo> {

  SlotCreationStrategy createInstance(T config);
}
