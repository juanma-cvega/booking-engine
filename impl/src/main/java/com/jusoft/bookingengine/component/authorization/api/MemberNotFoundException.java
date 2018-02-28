package com.jusoft.bookingengine.component.authorization.api;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException {

  private static final String MESSAGE = "Member %s not found";

  private final long memberId;

  public MemberNotFoundException(long memberId) {
    super(String.format(MESSAGE, memberId));
    this.memberId = memberId;
  }
}
