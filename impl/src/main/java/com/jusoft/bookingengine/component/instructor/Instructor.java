package com.jusoft.bookingengine.component.instructor;

import com.jusoft.bookingengine.component.instructor.api.BuildingDoesNotBelongToClubException;
import com.jusoft.bookingengine.component.instructor.api.PersonalInfo;
import com.jusoft.bookingengine.component.instructor.api.RegisterWithBuildingCommand;
import lombok.Data;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
class Instructor {

  private final long id;
  private final long clubId;
  private final Set<Long> registeredBuildingsId;
  private final PersonalInfo personalInfo;
  private final Set<String> supportedClassTypes;

  Instructor(long id, long clubId, PersonalInfo personalInfo) {
    this(id, clubId, new HashSet<>(), personalInfo, new HashSet<>());
  }

  Instructor registerOnBuilding(RegisterWithBuildingCommand command) {
    if (command.getClubId() != clubId) {
      throw new BuildingDoesNotBelongToClubException(command.getBuildingId(), clubId, id);
    }
    registeredBuildingsId.add(command.getBuildingId());
    return copy();
  }

  Instructor(long id, long clubId, Set<Long> registeredBuildingsId, PersonalInfo personalInfo, Set<String> supportedClassTypes) {
    Objects.requireNonNull(supportedClassTypes);
    Objects.requireNonNull(registeredBuildingsId);
    Objects.requireNonNull(personalInfo);
    this.id = id;
    this.clubId = clubId;
    this.registeredBuildingsId = registeredBuildingsId;
    this.personalInfo = personalInfo;
    this.supportedClassTypes = new HashSet<>(supportedClassTypes);
  }

  Instructor addClassTypes(List<String> classType) {
    supportedClassTypes.addAll(classType);
    return copy();
  }

  boolean isRegisteredIn(List<Long> buildingsId) {
    return !Collections.disjoint(registeredBuildingsId, buildingsId);
  }

  Instructor unregisterFromBuilding(long buildingId) {
    registeredBuildingsId.remove(buildingId);
    return copy();
  }

  Instructor unregisterClassTypes(List<String> classTypes) {
    supportedClassTypes.removeAll(classTypes);
    return copy();
  }

  private Instructor copy() {
    return new Instructor(id, clubId, new HashSet<>(registeredBuildingsId), personalInfo, new HashSet<>(supportedClassTypes));
  }
}
