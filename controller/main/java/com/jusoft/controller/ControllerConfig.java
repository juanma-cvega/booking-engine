package com.jusoft.controller;

import com.jusoft.controller.booking.BookingControllerConfig;
import com.jusoft.controller.slot.SlotControllerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SlotControllerConfig.class, BookingControllerConfig.class})
public class ControllerConfig {

}
