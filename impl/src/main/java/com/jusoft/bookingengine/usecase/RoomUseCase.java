package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.Room;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoomUseCase {

  private final RoomComponent roomComponent;

  public Room createRoom(CreateRoomCommand createRoomCommand) {
    return roomComponent.create(createRoomCommand);
  }
}
