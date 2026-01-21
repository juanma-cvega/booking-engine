package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToMemberCommand;
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
class Member {

    private final long id;
    private final long userId;
    private final long clubId;

    @NonNull private final Map<Long, MemberBuildingAccessConfig> buildings;

    static Member of(
            long memberId,
            long userId,
            long clubId,
            Map<Long, MemberBuildingAccessConfig> buildings) {
        return new Member(memberId, userId, clubId, buildings);
    }

    static Member of(long memberId, long userId, long clubId) {
        return new Member(memberId, userId, clubId, new HashMap<>());
    }

    public Map<Long, MemberBuildingAccessConfig> getBuildings() {
        return new HashMap<>(buildings);
    }

    public Member addTagsToBuilding(long buildingId, List<Tag> tags) {
        findOrCreateBuilding(buildingId).addTags(tags);
        return new Member(id, userId, clubId, buildings);
    }

    public Member addTagsToRoom(AddRoomTagsToMemberCommand command) {
        findOrCreateBuilding(command.buildingId())
                .addTagsToRoom(command.roomId(), command.status(), command.tags());
        return new Member(id, userId, clubId, buildings);
    }

    public List<Tag> getTagsFor(long buildingId, long roomId, SlotStatus status) {
        MemberBuildingAccessConfig buildingFound = findOrCreateBuilding(buildingId);
        List<Tag> tagsFound = new ArrayList<>(buildingFound.getTags());
        MemberRoomAccessConfig roomFound = findOrCreateRoom(roomId, buildingFound);
        tagsFound.addAll(roomFound.getTagsBySlotStatus().getOrDefault(status, new ArrayList<>()));
        return tagsFound;
    }

    private MemberRoomAccessConfig findOrCreateRoom(
            long roomId, MemberBuildingAccessConfig buildingFound) {
        return buildingFound.getRooms().computeIfAbsent(roomId, MemberRoomAccessConfig::of);
    }

    private MemberBuildingAccessConfig findOrCreateBuilding(long buildingId) {
        return buildings.computeIfAbsent(buildingId, MemberBuildingAccessConfig::of);
    }

    public boolean canBidIn(long buildingId, long roomId) {
        return findOrCreateBuilding(buildingId).canBidIn(roomId);
    }

    public Member addAccessToAuctionsFor(long buildingId, long roomId) {
        findOrCreateBuilding(buildingId).addAccessToAuctionsFor(roomId);
        return new Member(id, userId, clubId, buildings);
    }

    public Member removeAccessToAuctionsFor(long buildingId, long roomId) {
        findOrCreateBuilding(buildingId).removeAccessToAuctionsFor(roomId);
        return new Member(id, userId, clubId, buildings);
    }
}
