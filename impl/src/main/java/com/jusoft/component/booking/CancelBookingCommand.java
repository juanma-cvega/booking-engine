package com.jusoft.component.booking;

import lombok.Data;
import lombok.NonNull;

@Data
public class CancelBookingCommand {

    private final Long userId;
    private final Long bookingId;

    public CancelBookingCommand(@NonNull Long userId, @NonNull Long bookingId) {
        this.userId = userId;
        this.bookingId = bookingId;
    }
}
