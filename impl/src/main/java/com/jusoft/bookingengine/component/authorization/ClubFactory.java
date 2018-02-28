package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.BuildingView;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import com.jusoft.bookingengine.component.authorization.api.RoomView;
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
        .collect(toMap(BuildingView::getId, building -> building))
    );
  }

  private BuildingView createBuildingFrom(Building building) {
    return BuildingView.of(
      building.getId(),
      building.getRooms().values().stream()
        .map(this::createRoomFrom)
        .collect(toMap(RoomView::getId, room -> room)),
      building.getTags()
    );
  }

  private RoomView createRoomFrom(Room room) {
    return RoomView.of(
      room.getId(),
      room.getTagsBySlotStatus());
  }
}
