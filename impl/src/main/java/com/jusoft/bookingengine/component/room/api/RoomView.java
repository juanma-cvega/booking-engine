package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NonNull;

@Data
public class RoomView {

    private final long id;
    private final long clubId;
    private final long buildingId;

    @NonNull private final SlotCreationConfigInfo slotCreationConfigInfo;

    private final int slotDurationInMinutes;

    @NonNull private final List<OpenTime> openTimesPerDay;

    @NonNull private final List<DayOfWeek> availableDays;

    private RoomView(
            long id,
            long clubId,
            long buildingId,
            SlotCreationConfigInfo slotCreationConfigInfo,
            int slotDurationInMinutes,
            List<OpenTime> openTimesPerDay,
            List<DayOfWeek> availableDays) {
        this.id = id;
        this.clubId = clubId;
        this.buildingId = buildingId;
        this.slotCreationConfigInfo = slotCreationConfigInfo;
        this.slotDurationInMinutes = slotDurationInMinutes;
        this.openTimesPerDay = new ArrayList<>(openTimesPerDay);
        this.availableDays = new ArrayList<>(availableDays);
    }

    public static RoomView of(
            long id,
            long clubId,
            long buildingId,
            SlotCreationConfigInfo slotCreationConfigInfo,
            int slotDurationInMinutes,
            List<OpenTime> openTimesPerDay,
            List<DayOfWeek> availableDays) {
        return new RoomView(
                id,
                clubId,
                buildingId,
                slotCreationConfigInfo,
                slotDurationInMinutes,
                openTimesPerDay,
                availableDays);
    }

    public List<OpenTime> getOpenTimesPerDay() {
        return new ArrayList<>(openTimesPerDay);
    }

    public List<DayOfWeek> getAvailableDays() {
        return new ArrayList<>(availableDays);
    }
}
