package com.jusoft.bookingengine.controller.room.api;

import java.time.DayOfWeek;
import java.util.List;

public record RoomResource(
        long roomId,
        long clubId,
        long buildingId,
        int slotDurationInMinutes,
        int maxSlots,
        List<OpenTimeResource> openTimes,
        List<DayOfWeek> availableDays) {}
