package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.SlotAuthorizationConfig;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@EqualsAndHashCode(of = "id")
class ClubBuilding {

  private final long id;
  @NonNull
  private final Map<Long, ClubRoom> rooms;
  @NonNull
  private final List<Tag> tags;

  static ClubBuilding of(long buildingId) {
    return new ClubBuilding(buildingId, new HashMap<>(), new ArrayList<>());
  }

  public Map<Long, ClubRoom> getRooms() {
    return new HashMap<>(rooms);
  }

  public List<Tag> getTags() {
    return new ArrayList<>(tags);
  }

  boolean isAuthorisedFor(long roomId, List<Tag> memberTags, SlotStatus slotStatus) {
    boolean result = false;
    ClubRoom clubRoomFound = findOrCreateRoom(roomId);
    if (tagsContainAnyOf(memberTags)) {
      result = clubRoomFound.isAuthorised(memberTags, slotStatus);
    }
    return result;
  }

  void addTags(List<Tag> tags) {
    this.tags.addAll(tags);
  }

  void addTagsToRoom(long roomId, SlotStatus slotStatus, List<Tag> tags) {
    findOrCreateRoom(roomId).addTags(slotStatus, tags);
  }

  private ClubRoom findOrCreateRoom(long roomId) {
    return rooms.computeIfAbsent(roomId, ClubRoom::of);
  }

  private boolean tagsContainAnyOf(List<Tag> memberTags) {
    List<Tag> buildingsTags = new ArrayList<>(tags);
    buildingsTags.removeAll(memberTags);
    return tags.isEmpty() || tags.size() != buildingsTags.size();
  }

  void replaceSlotAuthorizationConfigToRoom(long roomId, SlotAuthorizationConfig slotAuthenticationConfig) {
    ClubRoom clubRoom = findOrCreateRoom(roomId).replaceSlotAuthenticationConfig(slotAuthenticationConfig);
    rooms.put(roomId, clubRoom);
  }

  public SlotStatus getSlotTypeFor(long roomId, ZonedDateTime slotCreationTime, Clock clock) {
    return findOrCreateRoom(roomId).getStatusFor(slotCreationTime, clock);

  }
}
