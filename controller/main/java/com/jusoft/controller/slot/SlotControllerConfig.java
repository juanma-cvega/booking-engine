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
    public SlotComponentRest slotComponentRest() {
        return new SlotComponentRest(slotComponent, slotCommandFactory(), slotResourceFactory());
    }

    private SlotCommandFactory slotCommandFactory() {
        return new SlotCommandFactory();
    }

    private SlotResourceFactory slotResourceFactory() {
        return new SlotResourceFactory();
    }
}
