package com.jusoft.bookingengine.component.authorization.api;

import lombok.Getter;

@Getter
public class UserNotAMemberException extends RuntimeException {

  private static final String MESSAGE = "User %s not a member of club %s";

  private final long userId;
  private final long clubId;

  public UserNotAMemberException(long userId, long clubId) {
    super(String.format(MESSAGE, userId, clubId));
    this.userId = userId;
    this.clubId = clubId;
  }
}
