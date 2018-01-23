package com.jusoft.bookingengine.fixture;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.LessBookingsWithinPeriodConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.NoAuctionConfigInfo;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import lombok.experimental.UtilityClass;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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
  public static final int MAX_SLOTS = 2;
  public static final boolean IS_ACTIVE = true;

  public static final int AUCTION_DURATION = 0;
  public static final int END_RANGE_TIME_IN_DAYS = 5;
  public static final MaxNumberOfSlotsStrategyConfigInfo MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO = new MaxNumberOfSlotsStrategyConfigInfo(MAX_SLOTS);
  public static final AuctionConfigInfo LESS_BOOKINGS_WITHIN_PERIOD_CONFIG = new LessBookingsWithinPeriodConfigInfo(AUCTION_DURATION, END_RANGE_TIME_IN_DAYS);
  public static final AuctionConfigInfo NO_AUCTION_CONFIG = new NoAuctionConfigInfo();

  public static final Function<Long, CreateRoomCommand> CREATE_ROOM_COMMAND = (buildingId) ->
    new CreateRoomCommand(buildingId, MAX_NUMBER_OF_SLOTS_STRATEGY_CONFIG_INFO,
      SLOT_DURATION_IN_MINUTES, OPEN_TIMES, AVAILABLE_DAYS, IS_ACTIVE, NO_AUCTION_CONFIG);

}
