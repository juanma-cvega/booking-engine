package com.jusoft.bookingengine.component.instructor.api;

import lombok.Getter;

@Getter
public class InstructorTimetableOverlappingException extends RuntimeException {

  private static final long serialVersionUID = 5470772047741583233L;

  private static final String MESSAGE = "Timetable %s overlaps with other timetables for instructor %s";

  private final long instructorId;
  private final transient Timetable timetable;

  public InstructorTimetableOverlappingException(long instructorId, Timetable timetable) {
    super(String.format(MESSAGE, timetable, instructorId));
    this.instructorId = instructorId;
    this.timetable = timetable;
  }
}
