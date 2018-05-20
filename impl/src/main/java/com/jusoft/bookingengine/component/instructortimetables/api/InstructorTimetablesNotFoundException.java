package com.jusoft.bookingengine.component.instructortimetables.api;

import lombok.Getter;

@Getter
public class InstructorTimetablesNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 8001776125377038308L;

  private static final String MESSAGE = "Instructor %s not found when searching for his timetables";

  private final long instructorId;

  public InstructorTimetablesNotFoundException(long instructorId) {
    super(String.format(MESSAGE, instructorId));
    this.instructorId = instructorId;
  }
}
