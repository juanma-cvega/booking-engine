package com.jusoft.bookingengine.fixtures;

import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.NoAuctionConfigInfo;
import lombok.experimental.UtilityClass;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class RoomFixtures {

  public static final long ROOM_ID = 1;
  public static final int SLOT_DURATION_IN_MINUTES = 30;
  public static final LocalTime START_TIME_MORNING = LocalTime.of(8, 0);
  public static final LocalTime END_TIME_MORNING = LocalTime.of(12, 0);
  public static final OpenTime OPEN_TIME_MORNING = new OpenTime(START_TIME_MORNING, END_TIME_MORNING);
  public static final LocalTime START_TIME_AFTERNOON = LocalTime.of(14, 0);
  public static final LocalTime START_TIME_NIGHT = LocalTime.of(20, 0);
  public static final LocalTime END_TIME_AFTERNOON = LocalTime.of(16, 0);
  public static final LocalTime END_TIME_NIGHT = LocalTime.of(22, 0);
  public static final OpenTime OPEN_TIME_AFTERNOON = new OpenTime(START_TIME_AFTERNOON, END_TIME_AFTERNOON);
  public static final OpenTime OPEN_TIME_NIGHT = new OpenTime(START_TIME_NIGHT, END_TIME_NIGHT);
  public static final List<OpenTime> OPEN_TIMES = Arrays.asList(OPEN_TIME_MORNING, OPEN_TIME_AFTERNOON, OPEN_TIME_NIGHT);
  public static final List<DayOfWeek> AVAILABLE_DAYS = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY);
  public static final boolean IS_ACTIVE = true;
  public static final AuctionConfigInfo AUCTION_CONFIG_INFO = new NoAuctionConfigInfo();

  public static final RoomCreatedEvent ROOM_CREATED_EVENT = RoomCreatedEvent.of(ROOM_ID, SLOT_DURATION_IN_MINUTES, OPEN_TIMES, AVAILABLE_DAYS, IS_ACTIVE, AUCTION_CONFIG_INFO);
}
