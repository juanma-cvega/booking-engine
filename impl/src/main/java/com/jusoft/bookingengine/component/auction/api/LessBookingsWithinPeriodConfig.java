package com.jusoft.bookingengine.component.auction.api;

import lombok.Data;

@Data
public class LessBookingsWithinPeriodConfig implements AuctionConfig {

  private final int endRangeTimeInDays;
}
