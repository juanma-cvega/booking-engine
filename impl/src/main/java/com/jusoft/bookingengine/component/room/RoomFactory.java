package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomFactory {

  private final Supplier<Long> idGenerator;

  Room createFrom(CreateRoomCommand createRoomCommand, long clubId) {
    return new Room(idGenerator.get(),
      clubId,
      createRoomCommand.getBuildingId(),
      createRoomCommand.getSlotCreationConfigInfo(),
      createRoomCommand.getSlotDurationInMinutes(),
      createRoomCommand.getOpenTimePerDay(),
      createRoomCommand.getAvailableDays());
  }

  RoomView createFrom(Room room) {
    return RoomView.of(room.getId(),
      room.getClubId(),
      room.getBuildingId(),
      room.getSlotCreationConfigInfo(),
      room.getSlotDurationInMinutes(),
      room.getOpenTimesPerDay(),
      room.getAvailableDays());
  }
}
