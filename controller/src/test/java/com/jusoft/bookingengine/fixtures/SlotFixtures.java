package com.jusoft.bookingengine.fixtures;

import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.controller.slot.SlotResource;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.ZonedDateTime;

import static com.jusoft.bookingengine.fixtures.RoomFixtures.ROOM_ID;

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

  public static final SlotResource SLOT_RESOURCE_1 = new SlotResource(SLOT_ID_1, ROOM_ID, START_TIME, END_TIME);

  public static final SlotCreatedEvent SLOT_CREATED_EVENT = SlotCreatedEvent.of(SLOT_ID_1, ROOM_ID, SLOT_STATE, OPEN_DATE);

}
