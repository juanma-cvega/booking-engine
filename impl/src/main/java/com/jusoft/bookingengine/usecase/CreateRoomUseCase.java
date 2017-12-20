package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateRoomUseCase {

  private final RoomComponent roomComponent;

  public RoomView createRoom(CreateRoomCommand createRoomCommand) {
    return roomComponent.create(createRoomCommand);
  }
}
