package com.jusoft.component.booking;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateBookingCommand {

    private final Long userId;
    private final Long roomId;
    private final Long slotId;

    public CreateBookingCommand(@NonNull Long userId, @NonNull Long roomId, @NonNull Long slotId) {
        this.userId = userId;
        this.roomId = roomId;
        this.slotId = slotId;
    }
}
