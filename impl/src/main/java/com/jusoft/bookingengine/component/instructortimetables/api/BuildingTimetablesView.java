package com.jusoft.bookingengine.component.instructortimetables.api;

import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Data(staticConstructor = "of")
public class BuildingTimetablesView {

  private final long buildingId;

  @NonNull
  private final Map<Long, RoomTimetableView> roomsTimetable;

  static BuildingTimetablesView of(long buildingId) {
    return of(buildingId, new HashMap<>());
  }

  Map<Long, RoomTimetableView> getRoomsTimetable() {
    return new HashMap<>(roomsTimetable);
  }
}
