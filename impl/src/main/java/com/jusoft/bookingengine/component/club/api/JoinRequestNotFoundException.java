package com.jusoft.bookingengine.component.club.api;

import lombok.Getter;

@Getter
public class JoinRequestNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Access request %s not found in club %s";

  private final long joinRequestId;
  private final long clubId;

  public JoinRequestNotFoundException(long joinRequestId, long clubId) {
    super(String.format(MESSAGE, joinRequestId, clubId));
    this.joinRequestId = joinRequestId;
    this.clubId = clubId;
  }
}
