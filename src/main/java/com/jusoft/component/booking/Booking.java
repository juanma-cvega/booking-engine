package com.jusoft.component.booking;

import com.jusoft.component.slot.Slot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Booking {

    private final long bookingId;
    private final long userId;
    private final LocalDateTime bookingTime;
    private final Slot slot;
}
