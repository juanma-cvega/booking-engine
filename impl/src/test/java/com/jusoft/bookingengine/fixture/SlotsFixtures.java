package com.jusoft.bookingengine.fixture;

import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.strategy.auctionwinner.api.AuctionConfigInfo;
import com.jusoft.bookingengine.strategy.auctionwinner.api.NoAuctionConfigInfo;
import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class SlotsFixtures {

  public static final ZonedDateTime END_TIME = ZonedDateTime.now().plus(5, ChronoUnit.DAYS);
  public static final ZonedDateTime START_TIME = ZonedDateTime.now().plus(1, ChronoUnit.DAYS);
  public static final OpenDate OPEN_DATE = OpenDate.of(START_TIME, END_TIME);
  public static final long SLOT_ID_1 = 2;
  public static final AuctionConfigInfo NO_AUCTION_CONFIG_INFO = NoAuctionConfigInfo.getInstance();
}
