package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

public record RoomView(
        long id,
        long clubId,
        long buildingId,
        SlotCreationConfigInfo slotCreationConfigInfo,
        int slotDurationInMinutes,
        List<OpenTime> openTimesPerDay,
        List<DayOfWeek> availableDays) {
    public RoomView {
        Objects.requireNonNull(slotCreationConfigInfo, "slotCreationConfigInfo must not be null");
        Objects.requireNonNull(openTimesPerDay, "openTimesPerDay must not be null");
        Objects.requireNonNull(availableDays, "availableDays must not be null");
        openTimesPerDay = List.copyOf(openTimesPerDay);
        availableDays = List.copyOf(availableDays);
    }
}
