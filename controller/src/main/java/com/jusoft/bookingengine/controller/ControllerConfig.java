package com.jusoft.bookingengine.controller;

import com.jusoft.bookingengine.controller.booking.BookingControllerConfig;
import com.jusoft.bookingengine.controller.club.ClubControllerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BookingControllerConfig.class, ClubControllerConfig.class})
public class ControllerConfig {}
