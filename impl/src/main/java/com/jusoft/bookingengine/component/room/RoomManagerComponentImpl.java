package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.GetTimetableCommand;
import com.jusoft.bookingengine.component.room.api.NextSlotConfig;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomNotFoundException;
import com.jusoft.bookingengine.component.room.api.RoomTimetable;
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
  public RoomView create(CreateRoomCommand createRoomCommand, long clubId) {
    Room room = roomFactory.createFrom(createRoomCommand, clubId);
    roomRepository.save(room);
    messagePublisher.publish(roomMessageFactory.roomCreatedEvent(room));
    return roomFactory.createFrom(room);
  }

  @Override
  public RoomView find(long roomId) {
    return roomFactory.createFrom(roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId)));
  }

  @Override
  public RoomTimetable getTimetableFor(GetTimetableCommand command) {
    Room room = findRoom(command.getRoomId());
    return room.getTimetableFor(command.getTimetableRequest());
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

  private Room findRoom(long roomId) {
    return roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
  }
}
