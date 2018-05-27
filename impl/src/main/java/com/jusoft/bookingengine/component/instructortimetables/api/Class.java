package com.jusoft.bookingengine.component.instructortimetables.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class Class {

  private final long classId;
  private final String classType;
}
