package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.ReplaceSlotAuthenticationConfigForRoomCommand;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(of = "id")
class Club {

    private final long id;

    @NonNull private final Map<Long, ClubBuildingAccessConfig> buildings;

    static Club of(long clubId, Map<Long, ClubBuildingAccessConfig> buildings) {
        return new Club(clubId, buildings);
    }

    static Club of(long clubId) {
        return new Club(clubId, new HashMap<>());
    }

    public Map<Long, ClubBuildingAccessConfig> getBuildings() {
        return new HashMap<>(buildings);
    }

    public boolean isAuthorizedFor(
            long buildingId, long roomId, List<Tag> memberTags, SlotStatus status) {
        ClubBuildingAccessConfig clubBuildingAccessConfigFound = findOrCreateBuilding(buildingId);
        return clubBuildingAccessConfigFound.isAuthorizedFor(roomId, memberTags, status);
    }

    public Club addTagsToBuilding(long buildingId, List<Tag> tags) {
        findOrCreateBuilding(buildingId).addTags(tags);
        return new Club(id, buildings);
    }

    public Club addTagsToRoom(AddRoomTagsToClubCommand command) {
        findOrCreateBuilding(command.getBuildingId())
                .addTagsToRoom(command.getRoomId(), command.getStatus(), command.getTags());
        return new Club(id, buildings);
    }

    private ClubBuildingAccessConfig findOrCreateBuilding(long buildingId) {
        return buildings.computeIfAbsent(buildingId, ClubBuildingAccessConfig::of);
    }

    public Club replaceSlotAuthorizationConfigForRoom(
            ReplaceSlotAuthenticationConfigForRoomCommand command) {
        findOrCreateBuilding(command.getBuildingId())
                .replaceSlotAuthorizationConfigToRoom(
                        command.getRoomId(), command.getSlotAuthenticationConfig());
        return new Club(id, buildings);
    }

    public SlotStatus getSlotTypeFor(
            long buildingId, long roomId, ZonedDateTime slotCreationTime, Clock clock) {
        return findOrCreateBuilding(buildingId).getSlotTypeFor(roomId, slotCreationTime, clock);
    }
}
