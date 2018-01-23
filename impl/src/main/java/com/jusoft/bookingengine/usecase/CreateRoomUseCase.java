package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.building.api.BuildingComponent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateRoomUseCase {

  private final RoomComponent roomComponent;
  private final BuildingComponent buildingComponent;

  public RoomView createRoom(CreateRoomCommand createRoomCommand) {
    BuildingView building = buildingComponent.find(createRoomCommand.getBuildingId());
    return roomComponent.create(createRoomCommand, building.getClubId());
  }
}
