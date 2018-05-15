package com.jusoft.bookingengine.component.instructor.api;

import lombok.Getter;

@Getter
public class InstructorNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 8341790718234418069L;

  private static final String MESSAGE = "Instructor %s not found";

  private final long instructorId;

  public InstructorNotFoundException(long instructorId) {
    super(String.format(MESSAGE, instructorId));
    this.instructorId = instructorId;
  }
}
