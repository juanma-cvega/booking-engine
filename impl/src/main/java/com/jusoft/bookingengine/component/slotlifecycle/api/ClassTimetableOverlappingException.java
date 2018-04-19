package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

@Getter
public class ClassTimetableOverlappingException extends RuntimeException {

  private static final long serialVersionUID = 11954984641554138L;

  private static final String MESSAGE = "Slot lifecycle manager for room %s already contains slots reserved overlapping with the configuration %s";

  private final long roomId;
  private final ClassTimetable classTimetable;

  public ClassTimetableOverlappingException(long roomId, ClassTimetable classTimetable) {
    super(String.format(MESSAGE, roomId, classTimetable));
    this.roomId = roomId;
    this.classTimetable = classTimetable;
  }
}
