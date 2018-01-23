package com.jusoft.bookingengine.fixtures;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.controller.slot.SlotResource;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.ZonedDateTime;

import static com.jusoft.bookingengine.fixtures.ClubFixtures.CLUB_ID;
import static com.jusoft.bookingengine.fixtures.RoomFixtures.ROOM_ID;

@UtilityClass
public class SlotFixtures {

  public static final long SLOT_ID_1 = 2;
  public static final long SLOT_ID_2 = 6;
  public static final long START_TIME = Instant.now().getEpochSecond();
  public static final long END_TIME = Instant.now().getEpochSecond();
  public static final ZonedDateTime START_DATE = ZonedDateTime.now();
  public static final ZonedDateTime END_DATE = ZonedDateTime.now().plusMinutes(10);

  public static final SlotResource SLOT_RESOURCE_1 = new SlotResource(SLOT_ID_1, ROOM_ID, START_TIME, END_TIME);

  public static final OpenNextSlotCommand OPEN_NEXT_SLOT_COMMAND = new OpenNextSlotCommand(ROOM_ID, CLUB_ID);
  public static final SlotCreatedEvent SLOT_CREATED_EVENT = new SlotCreatedEvent(SLOT_ID_1, ROOM_ID, CLUB_ID, START_DATE, END_DATE);

}
