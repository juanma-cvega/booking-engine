package com.jusoft.component.room.api;

import com.jusoft.component.room.Room;

public interface RoomComponent {

  Room create(CreateRoomCommand createRoomCommand);

  Room find(long roomId);

  void openNextSlotFor(long roomId);

  void scheduleComingSlotFor(long roomId);
}
