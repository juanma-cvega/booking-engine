package com.jusoft.bookingengine.component.authorization;

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
class MemberBuilding {

  private final long id;
  @NonNull
  private final Map<Long, MemberRoom> rooms;
  @NonNull
  private final List<Tag> tags;

  static MemberBuilding of(long buildingId) {
    return new MemberBuilding(buildingId, new HashMap<>(), new ArrayList<>());
  }

  public Map<Long, MemberRoom> getRooms() {
    return new HashMap<>(rooms);
  }

  public List<Tag> getTags() {
    return new ArrayList<>(tags);
  }

  void addTags(List<Tag> tags) {
    this.tags.addAll(tags);
  }

  void addTagsToRoom(long roomId, SlotStatus slotStatus, List<Tag> tags) {
    findOrCreateRoom(roomId).addTags(slotStatus, tags);
  }

  private MemberRoom findOrCreateRoom(long roomId) {
    return rooms.computeIfAbsent(roomId, MemberRoom::of);
  }
}
