package com.jusoft.component.fixtures;

import com.jusoft.component.slot.CreateSlotRequest;
import com.jusoft.component.slot.SlotResource;
import com.jusoft.component.slot.SlotResources;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static java.util.Arrays.asList;

public class SlotsFixtures {

    public static final long END_TIME = Instant.now().plus(5, ChronoUnit.DAYS).getEpochSecond();
    public static final long START_TIME = Instant.now().plus(1, ChronoUnit.DAYS).getEpochSecond();
    public static final CreateSlotRequest CREATE_SLOT_REQUEST = new CreateSlotRequest(ROOM_ID, START_TIME, END_TIME);
    public static final long SLOT_ID_1 = 2;
    public static final long SLOT_ID_2 = 6;
    public static final SlotResource SLOT_RESOURCE = new SlotResource(SLOT_ID_1, ROOM_ID, START_TIME, END_TIME);
    public static final SlotResource SLOT_RESOURCE_2 = new SlotResource(SLOT_ID_2, ROOM_ID, START_TIME, END_TIME);
    public static final SlotResources SLOT_RESOURCES = new SlotResources(asList(SLOT_RESOURCE, SLOT_RESOURCE_2));

    public static CreateSlotRequest createSlotRequestWith(long roomId) {
        return new CreateSlotRequest(roomId, START_TIME, END_TIME);
    }
}
