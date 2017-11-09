package com.jusoft.component.room;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.jusoft.component.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.component.room.RoomFixtures.ACTIVE;
import static com.jusoft.component.room.RoomFixtures.AVAILABLE_DAYS;
import static com.jusoft.component.room.RoomFixtures.MAX_SLOTS;
import static com.jusoft.component.room.RoomFixtures.OPEN_TIMES;
import static com.jusoft.component.room.RoomFixtures.SLOT_DURATION_IN_MINUTES;

@RunWith(MockitoJUnitRunner.class)
public class RoomTest {

    private final Room room;

    public RoomTest() {
        room = new Room(ROOM_ID,
                MAX_SLOTS,
                SLOT_DURATION_IN_MINUTES,
                OPEN_TIMES,
                AVAILABLE_DAYS,
                ACTIVE);
    }

    @Test
    public void test() {
//        room.findUpcomingSlot()
    }
}
