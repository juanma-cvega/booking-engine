package com.jusoft.bookingengine.component.slotlifecycle.api;

import lombok.Getter;

@Getter
public class ClassTimetableNotValidException extends RuntimeException {

  private static final long serialVersionUID = 1443853281846806694L;

  private static final String MESSAGE = "The class timetable %s is not valid for room %s";

  private final long roomId;
  private final ClassTimetable classTimetable;

  public ClassTimetableNotValidException(long roomId, ClassTimetable classTimetable) {
    super(String.format(MESSAGE, classTimetable, roomId));
    this.roomId = roomId;
    this.classTimetable = classTimetable;
  }
}
