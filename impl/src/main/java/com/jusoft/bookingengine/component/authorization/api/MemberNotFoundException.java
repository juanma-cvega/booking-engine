package com.jusoft.bookingengine.component.authorization.api;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -4076179876631949349L;

  private static final String MESSAGE = "Member %s not found";

  private final long memberId;

  public MemberNotFoundException(long memberId) {
    super(String.format(MESSAGE, memberId));
    this.memberId = memberId;
  }
}
