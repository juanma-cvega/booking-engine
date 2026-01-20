package com.jusoft.bookingengine.component.authorization;

import static com.jusoft.bookingengine.component.authorization.api.SlotStatus.EARLY_BIRD;
import static com.jusoft.bookingengine.component.authorization.api.SlotStatus.NORMAL;
import static java.time.ZonedDateTime.now;

import com.jusoft.bookingengine.component.authorization.api.SlotAuthorizationConfig;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
@EqualsAndHashCode(of = "id")
class ClubRoomAccessConfig {

    private final long id;
    private final SlotAuthorizationConfig slotAuthorizationConfig;

    @NonNull private final EnumMap<SlotStatus, List<Tag>> tagsBySlotStatus;

    static ClubRoomAccessConfig of(long roomId) {
        return of(roomId, SlotAuthorizationConfig.of(0, ChronoUnit.MILLIS));
    }

    static ClubRoomAccessConfig of(long roomId, SlotAuthorizationConfig slotAuthorizationConfig) {
        return of(roomId, slotAuthorizationConfig, new EnumMap<>(SlotStatus.class));
    }

    static ClubRoomAccessConfig of(
            long roomId,
            SlotAuthorizationConfig slotAuthorizationConfig,
            EnumMap<SlotStatus, List<Tag>> tagsBySlotStatus) {
        return new ClubRoomAccessConfig(roomId, slotAuthorizationConfig, tagsBySlotStatus);
    }

    public Map<SlotStatus, List<Tag>> getTagsBySlotStatus() {
        return new EnumMap<>(tagsBySlotStatus);
    }

    boolean isAuthorized(List<Tag> memberTags, SlotStatus status) {
        boolean isAuthorized = true;
        List<Tag> tagsRequired = tagsBySlotStatus.getOrDefault(status, new ArrayList<>());
        if (!tagsRequired.isEmpty()) {
            List<Tag> tagsNotInMemberList = new ArrayList<>(tagsRequired);
            tagsNotInMemberList.removeAll(memberTags);
            isAuthorized = tagsRequired.size() != tagsNotInMemberList.size();
        }
        return isAuthorized;
    }

    public void addTags(SlotStatus status, List<Tag> tags) {
        tagsBySlotStatus.computeIfAbsent(status, slotStatus -> new ArrayList<>()).addAll(tags);
    }

    public SlotStatus getStatusFor(ZonedDateTime slotCreationTime, Clock clock) {
        return slotCreationTime
                        .plus(
                                slotAuthorizationConfig.getAmount(),
                                slotAuthorizationConfig.getTemporalUnit())
                        .isAfter(now(clock))
                ? EARLY_BIRD
                : NORMAL;
    }

    public ClubRoomAccessConfig replaceSlotAuthenticationConfig(SlotAuthorizationConfig config) {
        return ClubRoomAccessConfig.of(id, config, tagsBySlotStatus);
    }
}
