package com.jusoft.bookingengine.component.instructor;

import com.jusoft.bookingengine.component.instructor.api.AddTimetableCommand;
import com.jusoft.bookingengine.component.instructor.api.InstructorTimetableNotPresentException;
import com.jusoft.bookingengine.component.instructor.api.InstructorTimetableOverlappingException;
import com.jusoft.bookingengine.component.instructor.api.PersonalInfo;
import com.jusoft.bookingengine.component.instructor.api.RemoveTimetableCommand;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toMap;

@Data
class Instructor {

  private final long id;
  private final long clubId;
  private final List<Long> buildings;
  private final PersonalInfo personalInfo;
  private final List<String> supportedClassTypes;
  private final Map<Long, BuildingTimetables> buildingsTimetables;

  Instructor(long id, long clubId, PersonalInfo personalInfo) {
    this(id, clubId, new ArrayList<>(), personalInfo, new ArrayList<>(), new HashMap<>());
  }

  Instructor(long id, long clubId, List<Long> buildings, PersonalInfo personalInfo, List<String> supportedClassTypes, Map<Long, BuildingTimetables> buildingsTimetables) {
    Objects.requireNonNull(supportedClassTypes);
    Objects.requireNonNull(buildingsTimetables);
    Objects.requireNonNull(buildings);
    Objects.requireNonNull(personalInfo);
    this.id = id;
    this.clubId = clubId;
    this.buildings = buildings;
    this.personalInfo = personalInfo;
    this.supportedClassTypes = new ArrayList<>(supportedClassTypes);
    this.buildingsTimetables = new HashMap<>(buildingsTimetables);
  }

  Instructor addToBuilding(long buildingId) {
    buildings.add(buildingId);
    return copy();
  }

  Instructor addClassType(String classType) {
    supportedClassTypes.add(classType);
    return copy();
  }

  boolean isRegisteredIn(long buildingId) {
    return buildings.contains(buildingId);
  }

  Instructor unregisterFromBuilding(long buildingId) {
    buildings.remove(buildingId);
    return copy();
  }

  Instructor unregisterClassTypes(List<String> classTypes) {
    supportedClassTypes.removeAll(classTypes);
    return copy();
  }

  Instructor addTimetable(AddTimetableCommand command) {
    BuildingTimetables buildingTimetables = buildingsTimetables.getOrDefault(command.getBuildingId(), BuildingTimetables.of(command.getBuildingId()));
    if (!buildingTimetables.addTimetable(command.getRoomId(), command.getTimetable())) {
      throw new InstructorTimetableOverlappingException(command.getInstructorId(), command.getTimetable());
    }
    buildingsTimetables.put(command.getBuildingId(), buildingTimetables);
    return copy();
  }

  Instructor removeTimetable(RemoveTimetableCommand command) {
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

  private Instructor copy() {
    return new Instructor(id, clubId, new ArrayList<>(buildings), personalInfo, new ArrayList<>(supportedClassTypes), copyBuildingTimetables());
  }

  private Map<Long, BuildingTimetables> copyBuildingTimetables() {
    return buildingsTimetables.values().stream()
      .map(buildingTimetables -> BuildingTimetables.of(
        buildingTimetables.getBuildingId(),
        new HashMap<>(buildingTimetables.getRoomsTimetable())))
      .collect(toMap(BuildingTimetables::getBuildingId, buildingTimetables -> buildingTimetables));
  }
}
