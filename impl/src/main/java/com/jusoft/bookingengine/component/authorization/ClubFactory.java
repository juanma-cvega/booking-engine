package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.ClubBuildingView;
import com.jusoft.bookingengine.component.authorization.api.ClubRoomView;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toMap;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClubFactory {

  ClubView createFrom(Club club) {
    return ClubView.of(
      club.getId(),
      club.getBuildings().values().stream()
        .map(this::createBuildingFrom)
        .collect(toMap(ClubBuildingView::getId, building -> building))
    );
  }

  private ClubBuildingView createBuildingFrom(ClubBuilding clubBuilding) {
    return ClubBuildingView.of(
      clubBuilding.getId(),
      clubBuilding.getRooms().values().stream()
        .map(this::createRoomFrom)
        .collect(toMap(ClubRoomView::getId, room -> room)),
      clubBuilding.getTags()
    );
  }

  private ClubRoomView createRoomFrom(ClubRoom clubRoom) {
    return ClubRoomView.of(
      clubRoom.getId(),
      clubRoom.getTagsBySlotStatus(),
      clubRoom.getSlotAuthorizationConfig());
  }
}
