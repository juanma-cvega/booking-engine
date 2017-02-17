package com.jusoft.booking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class Booking {

    private final long bookingId;
    private final long userId;
    private final LocalDateTime bookingTime;
    private final Slot slot;
}
