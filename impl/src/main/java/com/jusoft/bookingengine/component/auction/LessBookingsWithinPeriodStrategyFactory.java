package com.jusoft.bookingengine.component.auction;

import com.jusoft.bookingengine.component.auction.api.strategy.LessBookingsWithinPeriodConfigInfo;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LessBookingsWithinPeriodStrategyFactory implements AuctionWinnerStrategyFactory<LessBookingsWithinPeriodConfigInfo> {

  private final BookingComponent bookingComponent;
  private final Clock clock;

  @Override
  public LessBookingsWithinPeriodStrategy createInstance(LessBookingsWithinPeriodConfigInfo config) {
    return new LessBookingsWithinPeriodStrategy(bookingComponent, clock, config);
  }
}
