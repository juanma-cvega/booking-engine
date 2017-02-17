package com.jusoft.booking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CancelBookingRequest {

    private final long userId;
    private final long bookingId;
    private final LocalDateTime requestTime;
}
