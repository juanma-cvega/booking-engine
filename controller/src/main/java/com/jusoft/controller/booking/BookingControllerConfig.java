package com.jusoft.controller.booking;

import com.jusoft.component.booking.BookingComponent;
import com.jusoft.controller.slot.SlotResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingControllerConfig {

    @Autowired
    private BookingComponent bookingComponent;

    @Autowired
    private SlotResourceFactory slotResourceFactory;

    @Bean
    public BookingControllerRest bookingComponentRest() {
        return new BookingControllerRest(bookingComponent, bookingCommandFactory(), bookingResourceFactory());
    }

    private BookingCommandFactory bookingCommandFactory() {
        return new BookingCommandFactory();
    }

    private BookingResourceFactory bookingResourceFactory() {
        return new BookingResourceFactory(slotResourceFactory);
    }
}
