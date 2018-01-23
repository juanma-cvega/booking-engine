package com.jusoft.bookingengine.component.club.api;

import lombok.Getter;

@Getter
public class JoinRequestNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Access request %s not found in club %s";

  private final long accessRequestId;
  private final long clubId;

  public JoinRequestNotFoundException(long accessRequestId, long clubId) {
    super(String.format(MESSAGE, accessRequestId, clubId));
    this.accessRequestId = accessRequestId;
    this.clubId = clubId;
  }
}
