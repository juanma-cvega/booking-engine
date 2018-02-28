package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.Coordinates;
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
class Building {

  private final long id;
  @NonNull
  private final Map<Long, Room> rooms;
  @NonNull
  private final List<Tag> tags;

  static Building of(long buildingId) {
    return new Building(buildingId, new HashMap<>(), new ArrayList<>());
  }

  public Map<Long, Room> getRooms() {
    return new HashMap<>(rooms);
  }

  public List<Tag> getTags() {
    return new ArrayList<>(tags);
  }

  boolean isAuthorisedFor(Coordinates coordinates, List<Tag> memberTags) {
    boolean result = false;
    Room roomFound = findOrCreateRoom(coordinates.getRoomId());
    if (tagsContainAny(memberTags)) {
      result = roomFound.isAuthorised(coordinates.getSlotStatus(), memberTags);
    }
    return result;
  }

  void addTags(List<Tag> tags) {
    this.tags.addAll(tags);
  }

  void addTagsToRoom(long roomId, SlotStatus slotStatus, List<Tag> tags) {
    findOrCreateRoom(roomId).addTags(slotStatus, tags);
  }

  private Room findOrCreateRoom(long roomId) {
    return rooms.computeIfAbsent(roomId, Room::of);
  }

  private boolean tagsContainAny(List<Tag> memberTags) {
    List<Tag> buildingsTags = new ArrayList<>(tags);
    buildingsTags.removeAll(memberTags);
    return tags.isEmpty() || tags.size() != buildingsTags.size();
  }
}
