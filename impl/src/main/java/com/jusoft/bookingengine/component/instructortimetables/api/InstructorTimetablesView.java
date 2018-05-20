package com.jusoft.bookingengine.component.instructortimetables.api;

import lombok.Data;

import java.util.Map;

@Data(staticConstructor = "of")
public class InstructorTimetablesView {

  private final long id;
  private final Map<Long, BuildingTimetablesView> buildingsTimetable;
}
