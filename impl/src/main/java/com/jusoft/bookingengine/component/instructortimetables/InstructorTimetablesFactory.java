package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructor.api.Timetable;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorBuildingTimetablesView;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorRoomTimetablesView;
import com.jusoft.bookingengine.component.instructortimetables.api.InstructorTimetablesView;
import com.jusoft.bookingengine.component.instructortimetables.api.RoomTimetableView;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
class InstructorTimetablesFactory {

  InstructorTimetablesView createFrom(InstructorTimetables instructorTimetables) {
    return InstructorTimetablesView.of(instructorTimetables.getId());
  }

  InstructorBuildingTimetablesView createInstructorBuildingTimetablesViewFrom(InstructorTimetables instructorTimetables, long buildingId) {
    Validate.isTrue(instructorTimetables.getBuildingsTimetables().size() <= 1,
      "Unable to create object. The number of buildings to map from must be equal or less than 1");
    return InstructorBuildingTimetablesView.of(
      instructorTimetables.getId(),
      buildingId,
      getRoomsTimetables(instructorTimetables)
    );
  }

  private Map<Long, RoomTimetableView> getRoomsTimetables(InstructorTimetables instructorTimetables) {
    return instructorTimetables.getBuildingsTimetables().values().stream()
      .map(BuildingTimetables::getRoomsTimetable)
      .map(toRoomTimetablesView())
      .findFirst()
      .orElse(new HashMap<>());
  }

  private Function<Map<Long, RoomTimetable>, Map<Long, RoomTimetableView>> toRoomTimetablesView() {
    return roomTimetables -> roomTimetables.values().stream()
      .map(roomTimetable -> RoomTimetableView.of(roomTimetable.getRoomId(), roomTimetable.getTimetables()))
      .collect(Collectors.toMap(RoomTimetableView::getRoomId, roomTimetable -> roomTimetable));
  }

  InstructorRoomTimetablesView createInstructorRoomTimetablesViewFrom(InstructorTimetables instructorTimetables, long roomId) {
    Validate.isTrue(instructorTimetables.getBuildingsTimetables().size() <= 1,
      "Unable to create object. The number of buildings to map from must be equal or less than 1");
    instructorTimetables.getBuildingsTimetables().keySet().forEach(buildingId ->
      Validate.isTrue(instructorTimetables.getBuildingsTimetables().get(buildingId).getRoomsTimetable().size() <= 1,
        "Unable to create object. The number of rooms in the building to map from must be equal or less than 1"));
    return InstructorRoomTimetablesView.of(
      instructorTimetables.getId(),
      roomId,
      getRoomTimetablesOrDefaultToEmptyRoomTimetables(instructorTimetables, roomId)
    );
  }

  private Map<String, Timetable> getRoomTimetablesOrDefaultToEmptyRoomTimetables(InstructorTimetables instructorTimetables, long roomId) {
    return instructorTimetables.getBuildingsTimetables().values().stream()
      .map(BuildingTimetables::getRoomsTimetable)
      .map(roomsTimetables -> roomsTimetables.getOrDefault(roomId, RoomTimetable.of(roomId)))
      .map(RoomTimetable::getTimetables)
      .findFirst()
      .orElse(new HashMap<>());
  }
}
