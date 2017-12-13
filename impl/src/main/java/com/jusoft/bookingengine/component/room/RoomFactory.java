package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomFactory {

  private final Supplier<Long> idGenerator;

  Room createFrom(CreateRoomCommand createRoomCommand) {
    return new Room(idGenerator.get(),
      createRoomCommand.getMaxSlots(),
      createRoomCommand.getSlotDurationInMinutes(),
      createRoomCommand.getOpenTimePerDay(),
      createRoomCommand.getAvailableDays(),
      createRoomCommand.isActive(),
      createRoomCommand.getAuctionConfigInfo());
  }

  RoomView createFrom(Room room) {
    return new RoomView(room.getId(),
      room.getMaxSlots(),
      room.getSlotDurationInMinutes(),
      room.getOpenTimesPerDay(),
      room.getAvailableDays(),
      room.isActive(),
      room.getAuctionConfigInfo());
  }
}
