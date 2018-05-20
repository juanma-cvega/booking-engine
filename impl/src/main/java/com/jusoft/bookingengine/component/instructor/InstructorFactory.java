package com.jusoft.bookingengine.component.instructor;

import com.jusoft.bookingengine.component.instructor.api.CreateInstructorCommand;
import com.jusoft.bookingengine.component.instructor.api.InstructorView;
import com.jusoft.bookingengine.component.instructortimetables.api.RoomTimetableDetails;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
class InstructorFactory {

  private final Supplier<Long> idSupplier;

  Instructor createFrom(CreateInstructorCommand command) {
    return new Instructor(
      idSupplier.get(),
      command.getClubId(),
      command.getPersonalInfo());
  }

  InstructorView createFrom(Instructor instructor) {
    return InstructorView.of(
      instructor.getId(),
      instructor.getClubId(),
      new ArrayList<>(instructor.getRegisteredBuildingsId()),
      instructor.getPersonalInfo(),
      new ArrayList<>(instructor.getSupportedClassTypes()),
      getBuildingsTimetablesFrom(instructor.getBuildingsTimetables()));
  }

  private Map<Long, List<RoomTimetableDetails>> getBuildingsTimetablesFrom(Map<Long, BuildingTimetables> buildingsTimetables) {
    return buildingsTimetables.values().stream()
      .collect(toMap(BuildingTimetables::getBuildingId, this::toRoomsTimetable));
  }

  private List<RoomTimetableDetails> toRoomsTimetable(BuildingTimetables buildingTimetables) {
    return buildingTimetables.getRoomsTimetable().values().stream()
      .map(roomTimetable -> RoomTimetableDetails.of(roomTimetable.getRoomId(), roomTimetable.getTimetables()))
      .collect(toList());
  }
}
