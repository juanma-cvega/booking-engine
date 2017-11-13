package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.fixtures.CommonFixtures;
import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.Arrays.asList;

@UtilityClass
public class SlotsFixtures {

  public static final ZonedDateTime END_TIME = ZonedDateTime.now().plus(5, ChronoUnit.DAYS);
  public static final ZonedDateTime START_TIME = ZonedDateTime.now().plus(1, ChronoUnit.DAYS);
  public static final long SLOT_ID_1 = 2;
  public static final long SLOT_ID_2 = 6;

  public static final Slot SLOT_1 = new Slot(SLOT_ID_1, CommonFixtures.ROOM_ID, START_TIME, END_TIME);
  public static final Slot SLOT_2 = new Slot(SLOT_ID_2, CommonFixtures.ROOM_ID, START_TIME, END_TIME);
  public static final List<Slot> SLOTS = asList(SLOT_1, SLOT_2);

  public static final CreateSlotCommand CREATE_SLOT_COMMAND = new CreateSlotCommand(CommonFixtures.ROOM_ID, START_TIME, END_TIME);

  public static CreateSlotCommand createSlotRequestWith(long roomId) {
    return new CreateSlotCommand(roomId, START_TIME, END_TIME);
  }
}
