package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.Coordinates;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(of = "id")
class Club {

  private final long id;
  @NonNull
  private final Map<Long, Building> buildings;

  static Club of(long clubId, Map<Long, Building> buildings) {
    return new Club(clubId, buildings);
  }

  static Club of(long clubId) {
    return new Club(clubId, new HashMap<>());
  }

  public Map<Long, Building> getBuildings() {
    return new HashMap<>(buildings);
  }

  public boolean isAuthorisedFor(Coordinates coordinates, List<Tag> memberTags) {
    Building buildingFound = findOrCreateBuilding(coordinates.getBuildingId());
    return buildingFound.isAuthorisedFor(coordinates, memberTags);
  }

  public void addTagsToBuilding(long buildingId, List<Tag> tags) {
    findOrCreateBuilding(buildingId).addTags(tags);
  }

  public void addTagsToRoom(AddRoomTagsToClubCommand command) {
    findOrCreateBuilding(command.getBuildingId()).addTagsToRoom(command.getRoomId(), command.getStatus(), command.getTags());
  }

  private Building findOrCreateBuilding(long buildingId) {
    return buildings.computeIfAbsent(buildingId, Building::of);
  }
}
