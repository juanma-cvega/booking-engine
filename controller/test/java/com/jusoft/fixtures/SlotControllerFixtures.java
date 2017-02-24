package com.jusoft.fixtures;

import com.jusoft.controller.slot.CreateSlotRequest;
import com.jusoft.controller.slot.SlotResource;
import com.jusoft.controller.slot.SlotResources;

import java.time.Instant;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.slot.SlotsFixtures.SLOT_ID_1;
import static com.jusoft.component.slot.SlotsFixtures.SLOT_ID_2;
import static java.util.Arrays.asList;

public class SlotControllerFixtures {

    public static final long START_TIME = Instant.now().getEpochSecond();
    public static final long END_TIME = Instant.now().getEpochSecond();

    public static final SlotResource SLOT_RESOURCE_1 = new SlotResource(SLOT_ID_1, ROOM_ID, START_TIME, END_TIME);
    public static final SlotResource SLOT_RESOURCE_2 = new SlotResource(SLOT_ID_2, ROOM_ID, START_TIME, END_TIME);
    public static final SlotResources SLOT_RESOURCES = new SlotResources(asList(SLOT_RESOURCE_1, SLOT_RESOURCE_2));

    public static final CreateSlotRequest CREATE_SLOT_REQUEST = new CreateSlotRequest(ROOM_ID, START_TIME, END_TIME);
}
