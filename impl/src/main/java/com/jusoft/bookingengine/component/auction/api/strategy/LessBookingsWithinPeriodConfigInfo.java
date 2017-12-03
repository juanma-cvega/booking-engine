package com.jusoft.bookingengine.component.auction.api.strategy;

import lombok.Data;

@Data
public class LessBookingsWithinPeriodConfigInfo implements AuctionConfigInfo {

  private final int auctionDuration;
  private final int endRangeTimeInDays;

}
