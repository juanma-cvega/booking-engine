package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.publisher.Command;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class CreateRoomCommand implements Command {
    private final long buildingId;

    @NonNull private final SlotCreationConfigInfo slotCreationConfigInfo;

    private final int slotDurationInMinutes;

    @NonNull private final List<OpenTime> openTimePerDay;

    @NonNull private final List<DayOfWeek> availableDays;

    public List<OpenTime> getOpenTimePerDay() {
        return new ArrayList<>(openTimePerDay);
    }

    public List<DayOfWeek> getAvailableDays() {
        return new ArrayList<>(availableDays);
    }
}
