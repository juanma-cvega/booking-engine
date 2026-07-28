package com.jusoft.bookingengine.controller.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Clock;
import java.time.DayOfWeek;
import java.util.List;

public record CreateRoomRequest(
        @NotNull Long buildingId,
        @NotNull Integer slotDurationInMinutes,
        @NotNull Integer maxSlots,
        @Valid @NotEmpty List<OpenTimeRequest> openTimes,
        @NotEmpty List<DayOfWeek> availableDays) {

    public List<OpenTime> toOpenTimes(Clock clock) {
        return openTimes.stream()
                .map(
                        openTime ->
                                OpenTime.of(
                                        openTime.startTime(),
                                        openTime.endTime(),
                                        clock.getZone(),
                                        clock))
                .toList();
    }
}
