package com.jusoft.bookingengine.strategy.slotcreation;

import com.google.common.collect.ImmutableMap;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategyFactory;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategyRegistrar;
import java.time.Clock;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlotCreationStrategyConfig {

    @Autowired private SlotManagerComponent slotManagerComponent;

    @Autowired private Clock clock;

    @Bean
    public SlotCreationStrategyRegistrar slotCreationStrategyRegistrar() {
        return new SlotCreationStrategyRegistrar(factories());
    }

    private Map<Class<? extends SlotCreationConfigInfo>, SlotCreationStrategyFactory> factories() {
        return ImmutableMap.of(
                MaxNumberOfSlotsStrategyConfigInfo.class, maxNumberOfSlotsStrategyFactory());
    }

    private MaxNumberOfSlotsStrategyFactory maxNumberOfSlotsStrategyFactory() {
        return new MaxNumberOfSlotsStrategyFactory(slotManagerComponent, clock);
    }
}
