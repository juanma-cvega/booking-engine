package com.jusoft.component.slot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class SlotComponentConfig {

    @Bean
    public SlotComponent slotComponent() {
        return new SlotComponentInProcess(slotService(), slotResourceFactory());
    }

    private SlotService slotService() {
        return new SlotService(slotRepository(), slotFactory());
    }

    private SlotRepository slotRepository() {
        return new SlotRepositoryInMemory(new ConcurrentHashMap<>());
    }

    private SlotFactory slotFactory() {
        return new SlotFactory(idGenerator());
    }

    private Supplier<Long> idGenerator() {
        AtomicLong generator = new AtomicLong(1);
        return generator::getAndIncrement;
    }

    private SlotResourceFactory slotResourceFactory() {
        return new SlotResourceFactory();
    }
}
