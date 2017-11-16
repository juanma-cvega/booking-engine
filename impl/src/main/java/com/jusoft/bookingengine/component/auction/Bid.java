package com.jusoft.bookingengine.component.auction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class Bid {

  private final long userId;
  private final ZonedDateTime creationTime;
}
