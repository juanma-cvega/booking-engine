package com.jusoft.bookingengine.component.instructor.api;

import lombok.Data;

import java.util.Set;

@Data(staticConstructor = "of")
public class InstructorView {

  private final long id;
  private final long clubId;
  private final Set<Long> buildings;
  private final PersonalInfo personalInfo;
  private final Set<String> supportedClassTypes;
}
