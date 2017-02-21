package com.jusoft.fixtures;

import com.jusoft.slot.CreateSlotRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.jusoft.fixtures.CommonFixtures.ROOM_ID;

public class SlotsFixtures {

    public static final long END_TIME = Instant.now().plus(1, ChronoUnit.DAYS).getEpochSecond();
    public static final long START_TIME = Instant.now().getEpochSecond();
    public static final CreateSlotRequest CREATE_SLOT_REQUEST = new CreateSlotRequest(ROOM_ID, START_TIME, END_TIME);

    public static CreateSlotRequest createSlotRequestWith(long roomId) {
        return new CreateSlotRequest(roomId, START_TIME, END_TIME);
    }
}
