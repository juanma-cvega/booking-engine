package com.jusoft.bookingengine.fixtures;

import static com.jusoft.bookingengine.fixtures.RoomFixtures.ROOM_ID;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.slot.api.SlotUser;
import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.controller.slot.SlotResource;
import java.time.Instant;
import java.time.ZonedDateTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SlotFixtures {

    public static final long SLOT_ID_1 = 2;
    public static final long SLOT_ID_2 = 6;
    public static final long START_TIME = Instant.now().getEpochSecond();
    public static final long END_TIME = Instant.now().getEpochSecond();
    public static final ZonedDateTime START_DATE = ZonedDateTime.now();
    public static final ZonedDateTime END_DATE = ZonedDateTime.now().plusMinutes(10);
    public static final OpenDate OPEN_DATE = OpenDate.of(START_DATE, END_DATE);
    public static final SlotState SLOT_STATE = SlotState.CREATED;
    public static final String PERSON_USER_TYPE = "personUserType";
    public static final String CLASS_USER_TYPE = "classUserType";
    public static final SlotUser SLOT_USER =
            new SlotUser(CommonFixtures.USER_ID_1, PERSON_USER_TYPE);
    public static final SlotUser ANOTHER_SLOT_USER =
            new SlotUser(CommonFixtures.USER_ID_1, CLASS_USER_TYPE);

    public static final SlotResource SLOT_RESOURCE_1 =
            new SlotResource(SLOT_ID_1, ROOM_ID, START_TIME, END_TIME);

    public static final SlotCreatedEvent SLOT_CREATED_EVENT =
            new SlotCreatedEvent(SLOT_ID_1, ROOM_ID, SLOT_STATE, OPEN_DATE);
}
