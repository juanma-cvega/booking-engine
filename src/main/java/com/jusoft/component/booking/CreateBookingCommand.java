package com.jusoft.component.booking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBookingCommand {

    private final long userId;
    private final long roomId;
    private final long slotId;
    private final LocalDateTime requestTime;
}
