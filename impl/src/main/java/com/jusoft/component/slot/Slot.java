package com.jusoft.component.slot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Slot {

    private final long slotId;
    private final long roomId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

}
