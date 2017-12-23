package com.jusoft.bookingengine.fixtures;

import com.jusoft.bookingengine.controller.slot.SlotResource;

import java.time.Instant;

import static com.jusoft.bookingengine.fixtures.CommonFixtures.ROOM_ID;

public class SlotFixtures {

  public static final long SLOT_ID_1 = 2;
  public static final long SLOT_ID_2 = 6;
  public static final long START_TIME = Instant.now().getEpochSecond();
  public static final long END_TIME = Instant.now().getEpochSecond();

  public static final SlotResource SLOT_RESOURCE_1 = new SlotResource(SLOT_ID_1, ROOM_ID, START_TIME, END_TIME);

}
