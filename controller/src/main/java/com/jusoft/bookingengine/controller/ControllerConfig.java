package com.jusoft.bookingengine.controller;

import com.jusoft.bookingengine.controller.booking.BookingControllerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BookingControllerConfig.class)
public class ControllerConfig {

}
