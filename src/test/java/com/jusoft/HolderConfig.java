package com.jusoft;

import com.jusoft.booking.BookingHolder;
import com.jusoft.slot.SlotHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HolderConfig {

    @Bean
    public SlotHolder slotHolder() {
        return new SlotHolder();
    }

    @Bean
    public BookingHolder bookingHolder() {
        return new BookingHolder();
    }
}
