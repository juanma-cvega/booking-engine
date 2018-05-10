package com.jusoft.bookingengine.component.classmanager.api;

import lombok.Getter;

import java.util.List;

@Getter
public class ClassInUseException extends RuntimeException {

  private static final long serialVersionUID = 363140945552642139L;

  private static final String MESSAGE = "Class %s cannot be removed, it's been used by rooms %s";

  private final long classId;
  private final List<Long> roomsId;

  public ClassInUseException(long classId, List<Long> roomsId) {
    super(String.format(MESSAGE, classId, roomsId));
    this.classId = classId;
    this.roomsId = roomsId;
  }
}
