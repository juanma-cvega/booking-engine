package com.jusoft.bookingengine.fixture;

import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static com.jusoft.bookingengine.fixture.RoomFixtures.ROOM_ID;

@UtilityClass
public class SlotsFixtures {

  public static final ZonedDateTime END_TIME = ZonedDateTime.now().plus(5, ChronoUnit.DAYS);
  public static final ZonedDateTime START_TIME = ZonedDateTime.now().plus(1, ChronoUnit.DAYS);
  public static final long SLOT_ID_1 = 2;
  public static final long SLOT_ID_2 = 6;

  public static final CreateSlotCommand CREATE_SLOT_COMMAND = new CreateSlotCommand(ROOM_ID, START_TIME, END_TIME);

}
