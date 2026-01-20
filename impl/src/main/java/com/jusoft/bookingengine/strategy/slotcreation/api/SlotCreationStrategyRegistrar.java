package com.jusoft.bookingengine.strategy.slotcreation.api;

import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SlotCreationStrategyRegistrar {

    private final Map<Class<? extends SlotCreationConfigInfo>, SlotCreationStrategyFactory>
            factories;

    @SuppressWarnings("unchecked")
    public <T extends SlotCreationConfigInfo> SlotCreationStrategy createStrategyWith(T config) {
        return factories.get(config.getClass()).createInstance(config);
    }
}
