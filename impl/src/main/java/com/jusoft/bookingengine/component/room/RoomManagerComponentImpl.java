package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.NextSlotConfig;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomNotFoundException;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomManagerComponentImpl implements RoomManagerComponent {

  private final RoomRepository roomRepository;
  private final RoomFactory roomFactory;
  private final RoomMessageFactory roomMessageFactory;
  private final MessagePublisher messagePublisher;
  private final Clock clock;

  @Override
  public RoomView create(CreateRoomCommand createRoomCommand) {
    Room room = roomFactory.createFrom(createRoomCommand);
    roomRepository.save(room);
    messagePublisher.publish(roomMessageFactory.roomCreatedEvent(room));
    return roomFactory.createFrom(room);
  }

  @Override
  public RoomView find(long roomId) {
    return roomFactory.createFrom(roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId)));
  }

  @Override
  public NextSlotConfig findNextSlotOpenDate(ZonedDateTime lastSlotEndTime, long roomId) {
    Room room = findRoom(roomId);
    return room.findNextSlotDate(lastSlotEndTime, clock);
  }

  @Override
  public NextSlotConfig findFirstSlotOpenDate(long roomId) {
    Room room = findRoom(roomId);
    return room.findFirstSlotDate(clock);
  }

  @Override
  public void verifyAuctionRequirementFor(long roomId, long slotId) {
    Room room = findRoom(roomId);
    if (room.isAuctionRequired()) {
      messagePublisher.publish(roomMessageFactory.startAuctionCommand(room, slotId));
    }
  }

  private Room findRoom(long roomId) {
    return roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
  }
}
