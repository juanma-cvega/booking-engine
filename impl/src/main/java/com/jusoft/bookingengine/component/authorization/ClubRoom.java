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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.jusoft.bookingengine.component.authorization.api.SlotStatus.EARLY_BIRD;
import static com.jusoft.bookingengine.component.authorization.api.SlotStatus.NORMAL;
import static java.time.ZonedDateTime.now;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@EqualsAndHashCode(of = "id")
class ClubRoom {

  private final long id;
  private final SlotAuthorizationConfig slotAuthorizationConfig;
  @NonNull
  private final EnumMap<SlotStatus, List<Tag>> tagsBySlotStatus;

  static ClubRoom of(long roomId) {
    return of(roomId, SlotAuthorizationConfig.of(0, ChronoUnit.MILLIS));
  }

  static ClubRoom of(long roomId, SlotAuthorizationConfig slotAuthorizationConfig) {
    return of(roomId, slotAuthorizationConfig, new EnumMap<>(SlotStatus.class));
  }

  static ClubRoom of(long roomId, SlotAuthorizationConfig slotAuthorizationConfig, EnumMap<SlotStatus, List<Tag>> tagsBySlotStatus) {
    return new ClubRoom(roomId, slotAuthorizationConfig, tagsBySlotStatus);
  }

  public Map<SlotStatus, List<Tag>> getTagsBySlotStatus() {
    return new EnumMap<>(tagsBySlotStatus);
  }

  boolean isAuthorised(List<Tag> memberTags, SlotStatus status) {
    boolean isAuthorised = true;
    List<Tag> tagsRequired = tagsBySlotStatus.getOrDefault(status, new ArrayList<>());
    if (!tagsRequired.isEmpty()) {
      List<Tag> tagsNotInMemberList = new ArrayList<>(tagsRequired);
      tagsNotInMemberList.removeAll(memberTags);
      isAuthorised = tagsRequired.size() != tagsNotInMemberList.size();
    }
    return isAuthorised;
  }

  public void addTags(SlotStatus status, List<Tag> tags) {
    tagsBySlotStatus.computeIfAbsent(status, slotStatus -> new ArrayList<>()).addAll(tags);
  }

  public SlotStatus getStatusFor(ZonedDateTime slotCreationTime, Clock clock) {
    return slotCreationTime.plus(slotAuthorizationConfig.getAmount(), slotAuthorizationConfig.getTemporalUnit()).compareTo(now(clock)) > 0
      ? EARLY_BIRD
      : NORMAL;
  }

  public ClubRoom replaceSlotAuthenticationConfig(SlotAuthorizationConfig config) {
    return ClubRoom.of(id, config, tagsBySlotStatus);
  }
}
