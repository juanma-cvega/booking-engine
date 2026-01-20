package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.SlotAuthorizationConfig;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
class ClubBuildingAccessConfig {

    private final long id;

    @NonNull private final Map<Long, ClubRoomAccessConfig> rooms;

    @NonNull private final List<Tag> tags;

    static ClubBuildingAccessConfig of(long buildingId) {
        return new ClubBuildingAccessConfig(buildingId, new HashMap<>(), new ArrayList<>());
    }

    public Map<Long, ClubRoomAccessConfig> getRooms() {
        return new HashMap<>(rooms);
    }

    public List<Tag> getTags() {
        return new ArrayList<>(tags);
    }

    boolean isAuthorizedFor(long roomId, List<Tag> memberTags, SlotStatus slotStatus) {
        boolean result = false;
        ClubRoomAccessConfig clubRoomAccessConfigFound = findOrCreateRoom(roomId);
        if (tagsContainAnyOf(memberTags)) {
            result = clubRoomAccessConfigFound.isAuthorized(memberTags, slotStatus);
        }
        return result;
    }

    void addTags(List<Tag> tags) {
        this.tags.addAll(tags);
    }

    void addTagsToRoom(long roomId, SlotStatus slotStatus, List<Tag> tags) {
        findOrCreateRoom(roomId).addTags(slotStatus, tags);
    }

    private ClubRoomAccessConfig findOrCreateRoom(long roomId) {
        return rooms.computeIfAbsent(roomId, ClubRoomAccessConfig::of);
    }

    private boolean tagsContainAnyOf(List<Tag> memberTags) {
        List<Tag> buildingsTags = new ArrayList<>(tags);
        buildingsTags.removeAll(memberTags);
        return tags.isEmpty() || tags.size() != buildingsTags.size();
    }

    void replaceSlotAuthorizationConfigToRoom(
            long roomId, SlotAuthorizationConfig slotAuthenticationConfig) {
        ClubRoomAccessConfig clubRoomAccessConfig =
                findOrCreateRoom(roomId).replaceSlotAuthenticationConfig(slotAuthenticationConfig);
        rooms.put(roomId, clubRoomAccessConfig);
    }

    public SlotStatus getSlotTypeFor(long roomId, ZonedDateTime slotCreationTime, Clock clock) {
        return findOrCreateRoom(roomId).getStatusFor(slotCreationTime, clock);
    }
}
