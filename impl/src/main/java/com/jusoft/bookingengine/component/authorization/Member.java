package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
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
  private final long userId;
  private final long clubId;
  @NonNull
  private final Map<Long, MemberBuilding> buildings;

  static Member of(long memberId, long userId, long clubId, Map<Long, MemberBuilding> buildings) {
    return new Member(memberId, userId, clubId, buildings);
  }

  static Member of(long memberId, long userId, long clubId) {
    return new Member(memberId, userId, clubId, new HashMap<>());
  }

  public Map<Long, MemberBuilding> getBuildings() {
    return new HashMap<>(buildings);
  }

  public void addTagsToBuilding(long buildingId, List<Tag> tags) {
    findOrCreateBuilding(buildingId).addTags(tags);
  }

  public void addTagsToRoom(AddRoomTagsToMemberCommand command) {
    findOrCreateBuilding(command.getBuildingId()).addTagsToRoom(command.getRoomId(), command.getStatus(), command.getTags());
  }

  public List<Tag> getTagsFor(long buildingId, long roomId, SlotStatus status) {
    MemberBuilding buildingFound = findOrCreateBuilding(buildingId);
    List<Tag> tagsFound = new ArrayList<>(buildingFound.getTags());
    MemberRoom roomFound = findOrCreateRoom(roomId, buildingFound);
    tagsFound.addAll(roomFound.getTagsBySlotStatus().getOrDefault(status, new ArrayList<>()));
    return tagsFound;
  }

  private MemberRoom findOrCreateRoom(long roomId, MemberBuilding buildingFound) {
    return buildingFound.getRooms().computeIfAbsent(roomId, MemberRoom::of);
  }

  private MemberBuilding findOrCreateBuilding(long buildingId) {
    return buildings.computeIfAbsent(buildingId, MemberBuilding::of);
  }
}
