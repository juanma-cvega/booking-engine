package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.publisher.Command;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

public record CreateRoomCommand(
        long buildingId,
        SlotCreationConfigInfo slotCreationConfigInfo,
        int slotDurationInMinutes,
        List<OpenTime> openTimePerDay,
        List<DayOfWeek> availableDays)
        implements Command {
    public CreateRoomCommand {
        Objects.requireNonNull(slotCreationConfigInfo, "slotCreationConfigInfo must not be null");
        Objects.requireNonNull(openTimePerDay, "openTimePerDay must not be null");
        Objects.requireNonNull(availableDays, "availableDays must not be null");
        openTimePerDay = List.copyOf(openTimePerDay);
        availableDays = List.copyOf(availableDays);
    }
}
