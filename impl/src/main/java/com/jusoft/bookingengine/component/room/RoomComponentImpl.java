package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomNotFoundException;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.timer.OpenDate;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomComponentImpl implements RoomComponent {

  private final RoomRepository roomRepository;
  private final RoomFactory roomFactory;
  private final RoomEventFactory roomEventFactory;
  private final MessagePublisher messagePublisher;

  @Override
  public RoomView create(CreateRoomCommand createRoomCommand) {
    Room room = roomFactory.createFrom(createRoomCommand);
    roomRepository.save(room);
    messagePublisher.publish(roomEventFactory.roomCreatedEvent(room));
    return roomFactory.createFrom(room);
  }

  @Override
  public RoomView find(long roomId) {
    return roomFactory.createFrom(roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId)));
  }

  @Override
  public OpenDate findNextSlotOpenDate(ZonedDateTime lastSlotEndTime, long roomId) {
    Room room = findRoom(roomId);
    return room.findNextSlotDate(lastSlotEndTime);
  }

  @Override
  public OpenDate findFirstSlotOpenDate(long roomId) {
    Room room = findRoom(roomId);
    return room.findFirstSlotDate();
  }

  @Override
  public ZonedDateTime findNextSlotCreationTime(long roomId, int currentNumberOfSlotsOpen, ZonedDateTime nextSlotToFinishEndDate) {
    Room room = findRoom(roomId);
    return room.findUpcomingSlot(currentNumberOfSlotsOpen, nextSlotToFinishEndDate);
  }

  @Override
  public int getAuctionDurationFor(long roomId) {
    return findRoom(roomId).getAuctionConfigInfo().getAuctionDuration();
  }

  private Room findRoom(long roomId) {
    return roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
  }
}
