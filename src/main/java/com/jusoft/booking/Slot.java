package com.jusoft.booking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class Slot {

    private final long slotId;
    private final long roomId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

}
