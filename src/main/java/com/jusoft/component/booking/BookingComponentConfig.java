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
        return new BookingComponentInProcess(bookingService(), bookingResourceFactory(), bookingRequestFactory());
    }

    private BookingRequestFactory bookingRequestFactory() {
        return new BookingRequestFactory(instantSupplier);
    }

    private BookingResourceFactory bookingResourceFactory() {
        return new BookingResourceFactory(slotResourceFactory());
    }

    private SlotResourceFactory slotResourceFactory() {
        return new SlotResourceFactory();
    }

    private BookingService bookingService() {
        return new BookingService(slotComponent, slotResourceFactory(), bookingRepository(), bookingFactory());
    }

    private BookingFactory bookingFactory() {
        return new BookingFactory(idGenerator());
    }

    private Supplier<Long> idGenerator() {
        final AtomicLong idGenerator = new AtomicLong(1);
        return idGenerator::getAndIncrement;
    }

    private BookingRepository bookingRepository() {
        return new BookingRepositoryInMemory(new ConcurrentHashMap<>());
    }

}
