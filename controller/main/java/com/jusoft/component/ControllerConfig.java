package com.jusoft.component;

import com.jusoft.component.booking.BookingComponent;
import com.jusoft.component.slot.SlotComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Autowired
    private BookingComponent bookingComponent;

    @Autowired
    private SlotComponent slotComponent;

    @Bean
    public BookingComponent bookingComponentRest() {
        return new BookingComponentRest(bookingComponent);
    }

    @Bean
    public SlotComponent slotComponentRest() {
        return new SlotComponentRest(slotComponent);
    }
}
