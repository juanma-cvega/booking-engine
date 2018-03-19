package com.jusoft.bookingengine.component.authorization.api;

import lombok.Getter;

@Getter
public class UnauthorizedBidException extends RuntimeException {

  private static final String MESSAGE = "User %s is not authorized to bid in auctions for club %s, building %s and room %s";
  private final long userId;
  private final long clubId;
  private final long buildingId;
  private final long roomId;

  public UnauthorizedBidException(long userId, long clubId, long buildingId, long roomId) {
    super(String.format(MESSAGE, userId, clubId, buildingId, roomId));
    this.userId = userId;
    this.clubId = clubId;
    this.buildingId = buildingId;
    this.roomId = roomId;
  }
}
