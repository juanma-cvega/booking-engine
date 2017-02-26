package com.jusoft.controller.slot;

import com.jusoft.component.slot.SlotComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlotControllerConfig {

    @Autowired
    private SlotComponent slotComponent;

    @Bean
    public SlotControllerRest slotComponentRest() {
        return new SlotControllerRest(slotComponent, slotCommandFactory(), slotResourceFactory());
    }

    private SlotCommandFactory slotCommandFactory() {
        return new SlotCommandFactory();
    }

    @Bean
    public SlotResourceFactory slotResourceFactory() {
        return new SlotResourceFactory();
    }
}
