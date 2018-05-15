package com.jusoft.bookingengine.component.instructor.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data(staticConstructor = "of")
public class InstructorView {

  private final long id;
  private final long clubId;
  private final List<Long> buildings;
  private final PersonalInfo personalInfo;
  private final List<String> supportedClassTypes;
  private final Map<Long, List<RoomTimetableDetails>> buildingsTimetables;
}
