package com.jusoft.bookingengine.controller.slot;

import com.jusoft.bookingengine.usecase.slot.CreateSlotUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlotControllerConfig {

    @Bean
    public SlotControllerRest slotControllerRest(CreateSlotUseCase createSlotUseCase) {
        return new SlotControllerRest(createSlotUseCase);
    }
}
