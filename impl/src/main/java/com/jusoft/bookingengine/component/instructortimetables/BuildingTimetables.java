package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructor.api.Timetable;
import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Data(staticConstructor = "of")
class BuildingTimetables {

  private final long buildingId;

  @NonNull
  private final Map<Long, RoomTimetable> roomsTimetable;

  static BuildingTimetables of(long buildingId) {
    return of(buildingId, new HashMap<>());
  }

  boolean addTimetable(long roomId, Timetable timetable) {
    boolean result = false;
    if (!isOverlappingWithOtherRoomsTimetables(roomId, timetable)) {
      RoomTimetable roomTimetable = roomsTimetable.getOrDefault(roomId, RoomTimetable.of(roomId));
      if (roomTimetable.addTimetable(timetable)) {
        roomsTimetable.put(roomId, roomTimetable);
        result = true;
      }
    }
    return result;
  }

  private boolean isOverlappingWithOtherRoomsTimetables(long roomId, Timetable timetable) {
    return roomsTimetable.values().stream()
      .filter(byRoomDifferentThan(roomId))
      .anyMatch(roomTimetable -> roomTimetable.isOverlappingWith(timetable));
  }

  private Predicate<RoomTimetable> byRoomDifferentThan(long roomId) {
    return roomTimetable -> roomTimetable.getRoomId() != roomId;
  }

  Map<Long, RoomTimetable> getRoomsTimetable() {
    return new HashMap<>(roomsTimetable);
  }
}
