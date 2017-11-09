package com.jusoft.controller;

import com.jusoft.controller.booking.BookingControllerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BookingControllerConfig.class)
public class ControllerConfig {

}
