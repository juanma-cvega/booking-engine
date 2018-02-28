package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@EqualsAndHashCode(of = "id")
class Room {

  private final long id;
  @NonNull
  private final EnumMap<SlotStatus, List<Tag>> tagsBySlotStatus;

  static Room of(long roomId) {
    return new Room(roomId, new EnumMap<>(SlotStatus.class));
  }

  public Map<SlotStatus, List<Tag>> getTagsBySlotStatus() {
    return new EnumMap<>(tagsBySlotStatus);
  }

  boolean isAuthorised(SlotStatus slotStatus, List<Tag> memberTags) {
    List<Tag> tagsFound = tagsBySlotStatus.getOrDefault(slotStatus, new ArrayList<>());
    List<Tag> roomTags = new ArrayList<>(tagsFound);
    roomTags.removeAll(memberTags);
    return tagsFound.isEmpty() || tagsFound.size() != roomTags.size();
  }

  public void addTags(SlotStatus status, List<Tag> tags) {
    tagsBySlotStatus.computeIfAbsent(status, slotStatus -> new ArrayList<>()).addAll(tags);
  }
}
