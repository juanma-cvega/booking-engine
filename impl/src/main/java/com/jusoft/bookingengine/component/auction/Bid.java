package com.jusoft.bookingengine.component.auction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(exclude = "creationTime")
class Bid {

  private final long userId;
  @NonNull
  private final ZonedDateTime creationTime;
}
