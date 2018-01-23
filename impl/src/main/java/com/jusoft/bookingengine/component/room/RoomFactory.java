package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomFactory {

  private final Supplier<Long> idGenerator;
  private final Clock clock;

  Room createFrom(CreateRoomCommand createRoomCommand) {
    return new Room(idGenerator.get(),
      createRoomCommand.getBuildingId(),
      createRoomCommand.getSlotCreationConfigInfo(),
      createRoomCommand.getSlotDurationInMinutes(),
      createRoomCommand.getOpenTimePerDay(),
      createRoomCommand.getAvailableDays(),
      createRoomCommand.isActive(),
      createRoomCommand.getAuctionConfigInfo(),
      clock);
  }

  RoomView createFrom(Room room) {
    return new RoomView(room.getId(),
      room.getBuildingId(),
      room.getSlotCreationConfigInfo(),
      room.getSlotDurationInMinutes(),
      room.getOpenTimesPerDay(),
      room.getAvailableDays(),
      room.isActive(),
      room.getAuctionConfigInfo());
  }
}
