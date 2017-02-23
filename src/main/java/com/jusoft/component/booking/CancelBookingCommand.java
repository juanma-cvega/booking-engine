package com.jusoft.component.booking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CancelBookingCommand {

    private final long userId;
    private final long bookingId;
    private final LocalDateTime requestTime;
}
