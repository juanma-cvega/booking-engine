package com.jusoft.app;

import com.jusoft.booking.BookingComponentConfig;
import com.jusoft.slot.SlotComponentConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SlotComponentConfig.class, BookingComponentConfig.class})
public class AppConfig {
}
