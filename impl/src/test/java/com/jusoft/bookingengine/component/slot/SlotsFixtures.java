package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.component.fixtures.CommonFixtures;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class SlotsFixtures {

  public static final ZonedDateTime END_TIME = ZonedDateTime.now().plus(5, ChronoUnit.DAYS);
  public static final ZonedDateTime START_TIME = ZonedDateTime.now().plus(1, ChronoUnit.DAYS);
  public static final ZonedDateTime CREATION_TIME = ZonedDateTime.now();
  public static final long SLOT_ID_1 = 2;
  public static final long SLOT_ID_2 = 6;

  public static final Slot SLOT_1 = new Slot(SLOT_ID_1, CommonFixtures.ROOM_ID, CREATION_TIME, START_TIME, END_TIME);
  public static final Slot SLOT_2 = new Slot(SLOT_ID_2, CommonFixtures.ROOM_ID, CREATION_TIME, START_TIME, END_TIME);

}
