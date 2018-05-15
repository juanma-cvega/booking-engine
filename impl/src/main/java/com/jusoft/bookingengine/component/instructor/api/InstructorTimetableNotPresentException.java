package com.jusoft.bookingengine.component.instructor.api;

import lombok.Getter;

@Getter
public class InstructorTimetableNotPresentException extends RuntimeException {

  private static final long serialVersionUID = -8060703993536391730L;

  private static final String MESSAGE = "Instructor %s does not contain timetable %s in building %s in room %s";

  private final long instructorId;
  private final long buildingId;
  private final long roomId;
  private final transient Timetable timetable;

  public InstructorTimetableNotPresentException(long instructorId, long buildingId, long roomId, Timetable timetable) {
    super(String.format(MESSAGE, instructorId, timetable, buildingId, roomId));
    this.instructorId = instructorId;
    this.buildingId = buildingId;
    this.roomId = roomId;
    this.timetable = timetable;
  }
}
