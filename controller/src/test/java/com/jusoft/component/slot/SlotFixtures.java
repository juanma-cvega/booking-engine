package com.jusoft.component.slot;

import com.jusoft.controller.slot.CreateSlotRequest;
import com.jusoft.controller.slot.SlotResource;
import com.jusoft.controller.slot.SlotResources;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static com.jusoft.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.util.TimeUtil.getLocalDateTimeFrom;
import static java.util.Arrays.asList;

public class SlotFixtures {

    public static final long SLOT_ID_1 = 2;
    public static final long SLOT_ID_2 = 6;
    public static final long START_TIME = Instant.now().getEpochSecond();
    public static final LocalDateTime START_TIME_DATE = getLocalDateTimeFrom(START_TIME);
    public static final long END_TIME = Instant.now().getEpochSecond();
    public static final LocalDateTime END_TIME_DATE = getLocalDateTimeFrom(END_TIME);

    public static final CreateSlotCommand CREATE_SLOT_COMMAND = new CreateSlotCommand(ROOM_ID, START_TIME_DATE, END_TIME_DATE);
    public static final Slot SLOT_1 = new Slot(SLOT_ID_1, ROOM_ID, START_TIME_DATE, END_TIME_DATE);
    public static final Slot SLOT_2 = new Slot(SLOT_ID_2, ROOM_ID, START_TIME_DATE, END_TIME_DATE);
    public static final List<Slot> SLOTS = asList(SLOT_1, SLOT_2);

    public static final SlotResource SLOT_RESOURCE_1 = new SlotResource(SLOT_ID_1, ROOM_ID, START_TIME, END_TIME);
    public static final SlotResource SLOT_RESOURCE_2 = new SlotResource(SLOT_ID_2, ROOM_ID, START_TIME, END_TIME);
    public static final SlotResources SLOT_RESOURCES = new SlotResources(asList(SLOT_RESOURCE_1, SLOT_RESOURCE_2));

    public static final CreateSlotRequest CREATE_SLOT_REQUEST = new CreateSlotRequest(ROOM_ID, START_TIME, END_TIME);
}
