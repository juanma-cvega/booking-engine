package com.jusoft.bookingengine.controller;

import com.jusoft.bookingengine.controller.booking.BookingControllerConfig;
import com.jusoft.bookingengine.controller.room.RoomControllerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BookingControllerConfig.class, RoomControllerConfig.class})
public class ControllerConfig {}
