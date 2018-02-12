package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.building.api.BuildingComponent;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.room.api.RoomWithoutBuildingException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateRoomUseCase {

  private final RoomComponent roomComponent;
  private final BuildingComponent buildingComponent;

  public RoomView createRoom(CreateRoomCommand createRoomCommand) {
    if (!buildingComponent.isAvailable(createRoomCommand.getBuildingId())) {
      throw new RoomWithoutBuildingException(createRoomCommand.getBuildingId());
    }
    return roomComponent.create(createRoomCommand);
  }
}
