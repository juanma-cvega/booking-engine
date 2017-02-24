package com.jusoft.component.booking;

import lombok.Data;

@Data
public class CreateBookingCommand {

    private final long userId;
    private final long roomId;
    private final long slotId;
}
