package com.jusoft.bookingengine.controller.room.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.List;

public record CreateRoomRequest(
        @NotNull Long buildingId,
        @NotNull Integer slotDurationInMinutes,
        @NotNull Integer maxSlots,
        @NotEmpty List<OpenTimeRequest> openTimes,
        @NotEmpty List<DayOfWeek> availableDays) {}
