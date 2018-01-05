package com.jusoft.bookingengine.strategy.auctionwinner.api;

import lombok.Data;

@Data
public class LessBookingsWithinPeriodConfigInfo implements AuctionConfigInfo {

  private final int auctionDuration;
  private final int endRangeTimeInDays;

}
