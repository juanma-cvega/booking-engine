package com.jusoft.bookingengine.component.member.api;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Member of club %s for user %s not found";

  private final long clubId;
  private final long userId;

  public MemberNotFoundException(long userId, long clubId) {
    super(String.format(MESSAGE, clubId, userId));
    this.clubId = clubId;
    this.userId = userId;
  }
}
