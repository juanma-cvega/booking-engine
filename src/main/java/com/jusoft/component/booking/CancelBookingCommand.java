package com.jusoft.component.booking;

import lombok.Data;

@Data
public class CancelBookingCommand {

    private final long userId;
    private final long bookingId;
}
