package com.jusoft.bookingengine.controller.slot;

import com.jusoft.bookingengine.usecase.slot.CreateSlotUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlotControllerConfig {

    @Autowired private CreateSlotUseCase createSlotUseCase;

    @Bean
    public SlotResourceFactory slotResourceFactory() {
        return new SlotResourceFactory();
    }

    @Bean
    public SlotControllerRest slotControllerRest() {
        return new SlotControllerRest(createSlotUseCase, slotResourceFactory());
    }
}
