package com.jusoft.bookingengine.component.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.publisher.Event;
import java.time.DayOfWeek;
import java.util.List;

public record RoomCreatedEvent(
        long roomId,
        int slotDurationInMinutes,
        List<OpenTime> openTimesPerDay,
        List<DayOfWeek> availableDays)
        implements Event {}
