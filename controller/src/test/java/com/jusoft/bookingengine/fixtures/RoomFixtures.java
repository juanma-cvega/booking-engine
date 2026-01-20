package com.jusoft.bookingengine.fixtures;

import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.component.room.api.SlotRequiredEvent;
import com.jusoft.bookingengine.component.timer.OpenTime;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoomFixtures {

    private static final Clock CLOCK = Clock.systemUTC();
    public static final long ROOM_ID = 1;
    public static final int SLOT_DURATION_IN_MINUTES = 30;
    public static final LocalTime START_TIME_MORNING = LocalTime.of(8, 0);
    public static final LocalTime END_TIME_MORNING = LocalTime.of(12, 0);
    public static final OpenTime OPEN_TIME_MORNING =
            OpenTime.of(START_TIME_MORNING, END_TIME_MORNING, CLOCK.getZone(), CLOCK);
    public static final LocalTime START_TIME_AFTERNOON = LocalTime.of(14, 0);
    public static final LocalTime START_TIME_NIGHT = LocalTime.of(20, 0);
    public static final LocalTime END_TIME_AFTERNOON = LocalTime.of(16, 0);
    public static final LocalTime END_TIME_NIGHT = LocalTime.of(22, 0);
    public static final OpenTime OPEN_TIME_AFTERNOON =
            OpenTime.of(START_TIME_AFTERNOON, END_TIME_AFTERNOON, CLOCK.getZone(), CLOCK);
    public static final OpenTime OPEN_TIME_NIGHT =
            OpenTime.of(START_TIME_NIGHT, END_TIME_NIGHT, CLOCK.getZone(), CLOCK);
    public static final List<OpenTime> OPEN_TIMES =
            Arrays.asList(OPEN_TIME_MORNING, OPEN_TIME_AFTERNOON, OPEN_TIME_NIGHT);
    public static final List<DayOfWeek> AVAILABLE_DAYS =
            Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);

    public static final RoomCreatedEvent ROOM_CREATED_EVENT =
            new RoomCreatedEvent(ROOM_ID, SLOT_DURATION_IN_MINUTES, OPEN_TIMES, AVAILABLE_DAYS);
    public static final SlotRequiredEvent SLOT_REQUIRED_EVENT = new SlotRequiredEvent(ROOM_ID);
}
