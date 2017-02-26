package com.jusoft.component.booking;

import com.jusoft.component.slot.SlotComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

@Configuration
public class BookingComponentConfig {

    @Autowired
    private SlotComponent slotComponent;

    @Autowired
    private Supplier<LocalDateTime> instantSupplier;

    @Bean
    public BookingComponent bookingComponent() {
        return new BookingComponentImpl(slotComponent, bookingRepository(), bookingFactory(), instantSupplier);
    }

    private BookingFactory bookingFactory() {
        return new BookingFactory(idGenerator(), instantSupplier);
    }

    private Supplier<Long> idGenerator() {
        final AtomicLong idGenerator = new AtomicLong(1);
        return idGenerator::getAndIncrement;
    }

    private BookingRepository bookingRepository() {
        return new BookingRepositoryInMemory(new ConcurrentHashMap<>());
    }

}
