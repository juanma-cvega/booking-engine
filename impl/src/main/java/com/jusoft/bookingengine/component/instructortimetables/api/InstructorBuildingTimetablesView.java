package com.jusoft.bookingengine.component.instructortimetables.api;

import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Data(staticConstructor = "of")
public class InstructorBuildingTimetablesView {

  private final long instructorId;
  private final long buildingId;
  @NonNull
  private final Map<Long, RoomTimetableView> roomTimetables;

  Map<Long, RoomTimetableView> getRoomTimetables() {
    return new HashMap<>(roomTimetables);
  }
}
