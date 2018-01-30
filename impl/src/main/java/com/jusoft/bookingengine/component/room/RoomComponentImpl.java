package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomNotFoundException;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomComponentImpl implements RoomComponent {

  private final RoomRepository roomRepository;
  private final RoomFactory roomFactory;
  private final RoomEventFactory roomEventFactory;
  private final MessagePublisher messagePublisher;
  private final Clock clock;

  @Override
  public RoomView create(CreateRoomCommand createRoomCommand, long clubId) {
    Room room = roomFactory.createFrom(createRoomCommand);
    roomRepository.save(room);
    messagePublisher.publish(roomEventFactory.roomCreatedEvent(room, clubId));
    return roomFactory.createFrom(room);
  }

  @Override
  public RoomView find(long roomId) {
    return roomFactory.createFrom(roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId)));
  }

  @Override
  public OpenDate findNextSlotOpenDate(ZonedDateTime lastSlotEndTime, long roomId) {
    Room room = findRoom(roomId);
    return room.findNextSlotDate(lastSlotEndTime, clock);
  }

  @Override
  public OpenDate findFirstSlotOpenDate(long roomId) {
    Room room = findRoom(roomId);
    return room.findFirstSlotDate(clock);
  }

  @Override
  public int getAuctionDurationFor(long roomId) {
    return findRoom(roomId).getAuctionConfigInfo().getAuctionDuration();
  }

  private Room findRoom(long roomId) {
    return roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
  }
}
