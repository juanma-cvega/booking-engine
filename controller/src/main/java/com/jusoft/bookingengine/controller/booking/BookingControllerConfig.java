package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingControllerConfig {

    @Bean
    public BookingControllerRest bookingComponentRest(
            BookingManagerComponent bookingManagerComponent) {
        return new BookingControllerRest(
                bookingManagerComponent, new BookingCommandFactory(), new BookingResourceFactory());
    }
}
