package com.jusoft.bookingengine.component.authorization;

import static java.util.stream.Collectors.toMap;

import com.jusoft.bookingengine.component.authorization.api.ClubBuildingView;
import com.jusoft.bookingengine.component.authorization.api.ClubRoomView;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClubFactory {

    ClubView createFrom(Club club) {
        return new ClubView(
                club.getId(),
                club.getBuildings().values().stream()
                        .map(this::createBuildingFrom)
                        .collect(toMap(ClubBuildingView::id, building -> building)));
    }

    private ClubBuildingView createBuildingFrom(ClubBuildingAccessConfig clubBuildingAccessConfig) {
        return new ClubBuildingView(
                clubBuildingAccessConfig.getId(),
                clubBuildingAccessConfig.getRooms().values().stream()
                        .map(this::createRoomFrom)
                        .collect(toMap(ClubRoomView::id, room -> room)),
                clubBuildingAccessConfig.getTags());
    }

    private ClubRoomView createRoomFrom(ClubRoomAccessConfig clubRoomAccessConfig) {
        return new ClubRoomView(
                clubRoomAccessConfig.getId(),
                clubRoomAccessConfig.getTagsBySlotStatus(),
                clubRoomAccessConfig.getSlotAuthorizationConfig());
    }
}
