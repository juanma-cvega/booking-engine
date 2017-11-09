package com.jusoft.component.room.api;

import com.jusoft.component.shared.Event;
import com.jusoft.component.timer.OpenTime;
import lombok.Data;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.util.List;

@Data
public class RoomCreatedEvent implements Event {

    private final long roomId;
    private final int maxSlots;
    private final int slotDurationInMinutes;
    @NonNull
    private final List<OpenTime> openTimesPerDay;
    @NonNull
    private final List<DayOfWeek> availableDays;
    private final boolean active;
}
