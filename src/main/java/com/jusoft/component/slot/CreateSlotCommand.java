package com.jusoft.component.slot;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSlotCommand {

    private final long roomId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
}
