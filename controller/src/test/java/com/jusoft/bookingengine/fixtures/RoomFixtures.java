package com.jusoft.bookingengine.fixtures;

import static com.jusoft.bookingengine.fixtures.ClubFixtures.CLUB_ID;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.room.api.SlotRequiredEvent;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.controller.room.api.CreateRoomRequest;
import com.jusoft.bookingengine.controller.room.api.OpenTimeRequest;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationConfigInfo;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoomFixtures {

    public static final Clock FIXED_CLOCK =
            Clock.fixed(Instant.parse("2026-07-08T00:00:00Z"), ZoneOffset.UTC);
    private static final Clock CLOCK = FIXED_CLOCK;
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

    public static final long BUILDING_ID = 777;
    public static final int MAX_SLOTS = 10;
    public static final SlotCreationConfigInfo SLOT_CREATION_CONFIG_INFO =
            new MaxNumberOfSlotsStrategyConfigInfo(MAX_SLOTS);
    public static final CreateRoomCommand CREATE_ROOM_COMMAND =
            new CreateRoomCommand(
                    BUILDING_ID,
                    SLOT_CREATION_CONFIG_INFO,
                    SLOT_DURATION_IN_MINUTES,
                    OPEN_TIMES,
                    AVAILABLE_DAYS);
    public static final RoomView ROOM_VIEW =
            new RoomView(
                    ROOM_ID,
                    CLUB_ID,
                    BUILDING_ID,
                    SLOT_CREATION_CONFIG_INFO,
                    SLOT_DURATION_IN_MINUTES,
                    OPEN_TIMES,
                    AVAILABLE_DAYS);

    public static final List<OpenTimeRequest> OPEN_TIME_REQUESTS =
            List.of(
                    new OpenTimeRequest("08:00", "12:00"),
                    new OpenTimeRequest("14:00", "16:00"),
                    new OpenTimeRequest("20:00", "22:00"));
    public static final CreateRoomRequest CREATE_ROOM_REQUEST =
            new CreateRoomRequest(
                    BUILDING_ID,
                    SLOT_DURATION_IN_MINUTES,
                    MAX_SLOTS,
                    OPEN_TIME_REQUESTS,
                    AVAILABLE_DAYS);
}
