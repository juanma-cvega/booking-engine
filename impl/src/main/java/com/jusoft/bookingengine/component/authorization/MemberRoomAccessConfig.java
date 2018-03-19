package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@EqualsAndHashCode(of = "id")
class MemberRoomAccessConfig {

  private final long id;
  @NonNull
  private final EnumMap<SlotStatus, List<Tag>> tagsBySlotStatus;
  @Getter(AccessLevel.PRIVATE)
  private final boolean auctionAccess;

  static MemberRoomAccessConfig of(long roomId) {
    return of(roomId, new EnumMap<>(SlotStatus.class));
  }

  static MemberRoomAccessConfig of(long roomId, EnumMap<SlotStatus, List<Tag>> tagsBySlotStatus) {
    return new MemberRoomAccessConfig(roomId, tagsBySlotStatus, true);
  }

  public Map<SlotStatus, List<Tag>> getTagsBySlotStatus() {
    return new EnumMap<>(tagsBySlotStatus);
  }

  public void addTags(SlotStatus status, List<Tag> tags) {
    tagsBySlotStatus.computeIfAbsent(status, slotStatus -> new ArrayList<>()).addAll(tags);
  }


  public boolean canBid() {
    return auctionAccess;
  }

  public MemberRoomAccessConfig addAccessToAuctions() {
    return new MemberRoomAccessConfig(id, tagsBySlotStatus, true);
  }

  public MemberRoomAccessConfig removeAccessToAuctions() {
    return new MemberRoomAccessConfig(id, tagsBySlotStatus, false);
  }
}
