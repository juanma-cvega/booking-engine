package com.jusoft.bookingengine.component.instructortimetables;

import com.jusoft.bookingengine.component.instructor.api.AddTimetableCommand;
import com.jusoft.bookingengine.component.instructor.api.InstructorTimetableNotPresentException;
import com.jusoft.bookingengine.component.instructor.api.InstructorTimetableOverlappingException;
import com.jusoft.bookingengine.component.instructor.api.RemoveTimetableCommand;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

@Data
class InstructorTimetables {

  private final long id;
  private final Map<Long, BuildingTimetables> buildingsTimetables;

  InstructorTimetables(long id) {
    this(id, new HashMap<>());
  }

  InstructorTimetables(long id, Map<Long, BuildingTimetables> buildingsTimetables) {
    Objects.requireNonNull(buildingsTimetables);
    this.id = id;
    this.buildingsTimetables = new HashMap<>(buildingsTimetables);
  }

  InstructorTimetables addTimetable(AddTimetableCommand command) {
    BuildingTimetables buildingTimetables = buildingsTimetables.getOrDefault(command.getBuildingId(), BuildingTimetables.of(command.getBuildingId()));
    if (!buildingTimetables.addTimetable(command.getRoomId(), command.getTimetable())) {
      throw new InstructorTimetableOverlappingException(command.getInstructorId(), command.getTimetable());
    }
    buildingsTimetables.put(command.getBuildingId(), buildingTimetables);
    return copy();
  }

  InstructorTimetables removeTimetable(RemoveTimetableCommand command) {
    Validate.notNull(buildingsTimetables.get(command.getBuildingId()),
      String.format("Instructor %s does not have timetables created for building %s", command.getInstructorId(), command.getBuildingId()));
    Validate.notNull(buildingsTimetables.get(command.getBuildingId()).getRoomsTimetable().get(command.getRoomId()),
      String.format("Instructor %s does not have timetables created for building %s", command.getInstructorId(), command.getBuildingId()));
    RoomTimetable roomTimetable = buildingsTimetables.get(command.getBuildingId()).getRoomsTimetable().get(command.getRoomId());
    if (roomTimetable.removeTimetable(command.getTimetable())) {
      throw new InstructorTimetableNotPresentException(command.getInstructorId(), command.getBuildingId(), command.getRoomId(), command.getTimetable());
    }
    return copy();
  }

  private InstructorTimetables copy() {
    return new InstructorTimetables(id, copyBuildingTimetables());
  }

  private Map<Long, BuildingTimetables> copyBuildingTimetables() {
    return buildingsTimetables.values().stream()
      .map(buildingTimetables -> BuildingTimetables.of(
        buildingTimetables.getBuildingId(),
        new HashMap<>(buildingTimetables.getRoomsTimetable())))
      .collect(toMap(BuildingTimetables::getBuildingId, buildingTimetables -> buildingTimetables));
  }

  Map<Long, BuildingTimetables> getBuildingsTimetables() {
    return new HashMap<>(buildingsTimetables);
  }
}
