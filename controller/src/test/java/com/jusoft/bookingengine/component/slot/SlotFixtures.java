package com.jusoft.bookingengine.component.slot;

import com.jusoft.bookingengine.controller.slot.SlotResource;

import java.time.Instant;
import java.time.ZonedDateTime;

import static com.jusoft.bookingengine.fixtures.CommonFixtures.ROOM_ID;
import static com.jusoft.bookingengine.util.TimeUtil.getLocalDateTimeFrom;

public class SlotFixtures {

  public static final long SLOT_ID_1 = 2;
  public static final long SLOT_ID_2 = 6;
  public static final long START_TIME = Instant.now().getEpochSecond();
  public static final ZonedDateTime START_TIME_DATE = getLocalDateTimeFrom(START_TIME);
  public static final long END_TIME = Instant.now().getEpochSecond();
  public static final ZonedDateTime END_TIME_DATE = getLocalDateTimeFrom(END_TIME);
  public static final ZonedDateTime CREATION_TIME = ZonedDateTime.now();

  public static final Slot SLOT_1 = new Slot(SLOT_ID_1, ROOM_ID, CREATION_TIME, START_TIME_DATE, END_TIME_DATE);
  public static final Slot SLOT_2 = new Slot(SLOT_ID_2, ROOM_ID, CREATION_TIME, START_TIME_DATE, END_TIME_DATE);

  public static final SlotResource SLOT_RESOURCE_1 = new SlotResource(SLOT_ID_1, ROOM_ID, START_TIME, END_TIME);

}
