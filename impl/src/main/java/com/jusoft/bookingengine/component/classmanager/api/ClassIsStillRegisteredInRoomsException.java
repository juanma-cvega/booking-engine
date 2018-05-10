package com.jusoft.bookingengine.component.classmanager.api;

import lombok.Getter;

@Getter
public class ClassIsStillRegisteredInRoomsException extends RuntimeException {

  private static final long serialVersionUID = 3143508693342694846L;

  private static final String MESSAGE = "Class %s cannot be removed while it still contains rooms registrations";

  private final long classId;

  public ClassIsStillRegisteredInRoomsException(long classId) {
    super(String.format(MESSAGE, classId));
    this.classId = classId;
  }
}
