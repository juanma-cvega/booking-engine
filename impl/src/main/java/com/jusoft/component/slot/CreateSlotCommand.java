package com.jusoft.component.slot;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class CreateSlotCommand {

    private final Long roomId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public CreateSlotCommand(@NonNull Long roomId, @NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
