package com.jusoft.bookingengine.component.club.api;

import lombok.Getter;

@Getter
public class ClubAuthorizationException extends RuntimeException {

  private static final String MESSAGE = "User %s has no admin rights in club %s";

  private final long userId;

  public ClubAuthorizationException(long userId, long clubId) {
    super(String.format(MESSAGE, userId, clubId));
    this.userId = userId;
  }
}
