package com.jusoft.bookingengine.component.room;

import com.jusoft.bookingengine.component.auction.api.AuctionWinnerStrategyType;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.room.api.RoomNotFoundException;
import com.jusoft.bookingengine.component.scheduler.SchedulerComponent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import com.jusoft.bookingengine.component.slot.Slot;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
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
  private final SlotComponent slotComponent;
  private final SchedulerComponent schedulerComponent;
  private final Clock clock;

  @Override
  public Room create(CreateRoomCommand createRoomCommand) {
    Room room = roomFactory.createFrom(createRoomCommand);
    roomRepository.save(room);
    messagePublisher.publish(roomEventFactory.roomCreatedEvent(room));
    return room;
  }

  @Override
  public Room find(long roomId) {
    return roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
  }

  @Override
  public void openNextSlotFor(long roomId) {
    Room room = findRoom(roomId);
    room.openNextSlot(slotComponent, clock);
  }

  @Override
  public void scheduleComingSlotFor(long roomId) {
    Room room = findRoom(roomId);
    UpcomingSlot upcomingSlot = room.findUpcomingSlot(slotComponent, clock);
    schedulerComponent.schedule(taskBuilder -> taskBuilder
      .executionTime(upcomingSlot.getCreationTime())
      .event(roomEventFactory.openNextSlotCommand(upcomingSlot.getRoomId())));
  }

  @Override
  public ZonedDateTime findAuctionEndTimeFor(long roomId, long slotId) {
    Room room = findRoom(roomId);
    Slot slot = slotComponent.find(slotId, roomId);
    return slot.getCreationTime().plusMinutes(room.getAuctionTime());
  }

  @Override
  public AuctionWinnerStrategyType findAuctionWinnerStrategyTypeFor(long roomId) {
    return findRoom(roomId).getStrategyType();
  }

  private Room findRoom(long roomId) {
    return roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
  }
}
