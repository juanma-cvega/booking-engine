package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.Coordinates;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@EqualsAndHashCode(of = "id")
class Member {

  private final long id;
  @NonNull
  private final Map<Long, Building> buildings;

  static Member of(long memberId, Map<Long, Building> buildings) {
    return new Member(memberId, buildings);
  }

  static Member of(long memberId) {
    return new Member(memberId, new HashMap<>());
  }

  public Map<Long, Building> getBuildings() {
    return new HashMap<>(buildings);
  }

  public void addTagsToBuilding(long buildingId, List<Tag> tags) {
    findOrCreateBuilding(buildingId).addTags(tags);
  }

  public void addTagsToRoom(AddRoomTagsToMemberCommand command) {
    findOrCreateBuilding(command.getBuildingId()).addTagsToRoom(command.getRoomId(), command.getStatus(), command.getTags());
  }

  public List<Tag> getTagsFor(Coordinates coordinates) {
    Building buildingFound = findOrCreateBuilding(coordinates.getBuildingId());
    List<Tag> tagsFound = new ArrayList<>(buildingFound.getTags());
    Room roomFound = findOrCreateRoom(coordinates, buildingFound);
    tagsFound.addAll(roomFound.getTagsBySlotStatus().getOrDefault(coordinates.getSlotStatus(), new ArrayList<>()));
    return tagsFound;
  }

  private Room findOrCreateRoom(Coordinates coordinates, Building buildingFound) {
    return buildingFound.getRooms().computeIfAbsent(coordinates.getRoomId(), Room::of);
  }

  private Building findOrCreateBuilding(long buildingId) {
    return buildings.computeIfAbsent(buildingId, Building::of);
  }
}
