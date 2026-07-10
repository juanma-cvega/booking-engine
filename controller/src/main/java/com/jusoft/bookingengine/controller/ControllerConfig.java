package com.jusoft.bookingengine.controller;

import com.jusoft.bookingengine.controller.booking.BookingControllerConfig;
import com.jusoft.bookingengine.controller.slot.SlotControllerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BookingControllerConfig.class, SlotControllerConfig.class})
public class ControllerConfig {}
