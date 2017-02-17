package com.jusoft.booking;

import com.jusoft.slot.SlotResource;
import lombok.Data;

@Data
public class BookingResource {

    private final long bookingId;
    private final long userId;
    private final long bookingTime;
    private final SlotResource slot;
}
