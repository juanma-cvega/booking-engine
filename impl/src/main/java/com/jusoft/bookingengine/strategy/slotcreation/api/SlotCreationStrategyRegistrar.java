package com.jusoft.bookingengine.strategy.slotcreation.api;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class SlotCreationStrategyRegistrar {

  private final Map<Class<? extends SlotCreationConfigInfo>, SlotCreationStrategyFactory> factories;

  @SuppressWarnings("unchecked")
  public <T extends SlotCreationConfigInfo> SlotCreationStrategy createStrategyWith(T config) {
    return factories.get(config.getClass()).createInstance(config);
  }
}
