package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
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
class MemberBuildingAccessConfig {

    private final long id;

    @NonNull private final Map<Long, MemberRoomAccessConfig> rooms;

    @NonNull private final List<Tag> tags;

    static MemberBuildingAccessConfig of(long buildingId) {
        return new MemberBuildingAccessConfig(buildingId, new HashMap<>(), new ArrayList<>());
    }

    public Map<Long, MemberRoomAccessConfig> getRooms() {
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

    boolean canBidIn(long roomId) {
        return findOrCreateRoom(roomId).canBid();
    }

    public void addAccessToAuctionsFor(long roomId) {
        MemberRoomAccessConfig config = findOrCreateRoom(roomId).addAccessToAuctions();
        rooms.put(roomId, config);
    }

    public void removeAccessToAuctionsFor(long roomId) {
        MemberRoomAccessConfig config = findOrCreateRoom(roomId).removeAccessToAuctions();
        rooms.put(roomId, config);
    }

    private MemberRoomAccessConfig findOrCreateRoom(long roomId) {
        return rooms.computeIfAbsent(roomId, MemberRoomAccessConfig::of);
    }
}
