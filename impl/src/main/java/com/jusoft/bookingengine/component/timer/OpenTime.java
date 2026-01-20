package com.jusoft.bookingengine.component.timer;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class OpenTime {

    @NonNull private final SystemLocalTime startTime;

    @NonNull private final SystemLocalTime endTime;

    public static OpenTime of(
            String startTime, String endTime, ZoneId sourceZone, Clock systemClock) {
        return new OpenTime(
                SystemLocalTime.ofToday(startTime, sourceZone, systemClock),
                SystemLocalTime.ofToday(endTime, sourceZone, systemClock));
    }

    public static OpenTime of(
            LocalTime startTime, LocalTime endTime, ZoneId sourceZone, Clock systemClock) {
        return new OpenTime(
                SystemLocalTime.ofToday(startTime, sourceZone, systemClock),
                SystemLocalTime.ofToday(endTime, sourceZone, systemClock));
    }
}
