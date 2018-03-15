package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.Coordinates;
import com.jusoft.bookingengine.component.authorization.api.ReplaceSlotAuthenticationConfigForRoomCommand;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(of = "id")
class Club {

  private final long id;
  @NonNull
  private final Map<Long, ClubBuilding> buildings;

  static Club of(long clubId, Map<Long, ClubBuilding> buildings) {
    return new Club(clubId, buildings);
  }

  static Club of(long clubId) {
    return new Club(clubId, new HashMap<>());
  }

  public Map<Long, ClubBuilding> getBuildings() {
    return new HashMap<>(buildings);
  }

  public boolean isAuthorisedFor(Coordinates coordinates, List<Tag> memberTags, SlotStatus status) {
    ClubBuilding clubBuildingFound = findOrCreateBuilding(coordinates.getBuildingId());
    return clubBuildingFound.isAuthorisedFor(coordinates.getRoomId(), memberTags, status);
  }

  public void addTagsToBuilding(long buildingId, List<Tag> tags) {
    findOrCreateBuilding(buildingId).addTags(tags);
  }

  public void addTagsToRoom(AddRoomTagsToClubCommand command) {
    findOrCreateBuilding(command.getBuildingId()).addTagsToRoom(command.getRoomId(), command.getStatus(), command.getTags());
  }

  private ClubBuilding findOrCreateBuilding(long buildingId) {
    return buildings.computeIfAbsent(buildingId, ClubBuilding::of);
  }

  public void replaceSlotAuthorizationConfigForRoom(ReplaceSlotAuthenticationConfigForRoomCommand command) {
    findOrCreateBuilding(command.getBuildingId()).replaceSlotAuthorizationConfigToRoom(command.getRoomId(), command.getSlotAuthenticationConfig());
  }

  public SlotStatus getSlotTypeFor(Coordinates coordinates, Clock clock) {
    return findOrCreateBuilding(coordinates.getBuildingId())
      .getSlotTypeFor(coordinates.getRoomId(), coordinates.getSlotCreationTime(), clock);
  }
}
