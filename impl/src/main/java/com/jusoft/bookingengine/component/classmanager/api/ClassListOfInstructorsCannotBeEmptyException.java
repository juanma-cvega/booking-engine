package com.jusoft.bookingengine.component.classmanager.api;

import lombok.Getter;

@Getter
public class ClassListOfInstructorsCannotBeEmptyException extends RuntimeException {

  private static final long serialVersionUID = -4971792153420152974L;

  private static final String MESSAGE = "Class %s cannot remove last instructor %s as the list would be empty";

  private final long classId;
  private final long instructorId;

  public ClassListOfInstructorsCannotBeEmptyException(long classId, long instructorId) {
    this.classId = classId;
    this.instructorId = instructorId;
  }
}
