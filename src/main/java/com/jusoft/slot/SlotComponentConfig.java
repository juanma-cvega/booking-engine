package com.jusoft.slot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class SlotComponentConfig {

    @Bean
    public SlotComponent slotComponentInProcess() {
        return new SlotComponentInProcess(slotService(), slotResourceFactory());
    }

    @Bean
    public SlotComponent slotComponentRest() {
        return new SlotComponentRest(slotComponentInProcess());
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
