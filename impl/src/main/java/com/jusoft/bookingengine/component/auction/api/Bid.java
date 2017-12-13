package com.jusoft.bookingengine.component.auction.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.ZonedDateTime;

@Data
@EqualsAndHashCode(exclude = "creationTime")
public class Bid {

  private final long userId;
  @NonNull
  private final ZonedDateTime creationTime;
}
