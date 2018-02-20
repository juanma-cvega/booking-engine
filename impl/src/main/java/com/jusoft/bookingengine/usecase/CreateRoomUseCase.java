package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.room.api.RoomWithoutBuildingException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateRoomUseCase {

  private final RoomManagerComponent roomManagerComponent;
  private final BuildingManagerComponent buildingManagerComponent;

  public RoomView createRoom(CreateRoomCommand createRoomCommand) {
    if (!buildingManagerComponent.isAvailable(createRoomCommand.getBuildingId())) {
      throw new RoomWithoutBuildingException(createRoomCommand.getBuildingId());
    }
    return roomManagerComponent.create(createRoomCommand);
  }
}
