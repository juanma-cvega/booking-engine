package com.jusoft.slot;

import lombok.Data;

@Data
public class SlotResource {

    private final long slotId;
    private final long roomId;
    private final long startDate;
    private final long endDate;
}
