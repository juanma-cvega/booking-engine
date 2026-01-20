package com.jusoft.bookingengine.component.slot.api;

import com.jusoft.bookingengine.component.timer.OpenDate;
import java.time.ZonedDateTime;
import java.util.Objects;

public record SlotView(
        long id,
        long roomId,
        long buildingId,
        long clubId,
        SlotState state,
        ZonedDateTime creationTime,
        OpenDate openDate) {
    public SlotView {
        Objects.requireNonNull(state, "state must not be null");
        Objects.requireNonNull(creationTime, "creationTime must not be null");
        Objects.requireNonNull(openDate, "openDate must not be null");
    }
}
