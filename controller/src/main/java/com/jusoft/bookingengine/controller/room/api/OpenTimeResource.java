package com.jusoft.bookingengine.controller.room.api;

import com.jusoft.bookingengine.component.timer.OpenTime;

public record OpenTimeResource(String startTime, String endTime) {

    public static OpenTimeResource from(OpenTime openTime) {
        return new OpenTimeResource(
                openTime.getStartTime().getLocalTime().toString(),
                openTime.getEndTime().getLocalTime().toString());
    }
}
