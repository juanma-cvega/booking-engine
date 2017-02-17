package com.jusoft.slot;

import lombok.Data;

@Data
public class CreateSlotRequest {

    private final long roomId;
    private final long startTime;
    private final long endTime;
}
