package com.jusoft.component.room;

import com.jusoft.component.room.api.CreateRoomCommand;
import com.jusoft.component.room.api.RoomComponent;
import com.jusoft.component.room.api.RoomNotFoundException;
import com.jusoft.component.scheduler.SchedulerComponent;
import com.jusoft.component.shared.MessagePublisher;
import com.jusoft.component.slot.api.SlotComponent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;

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
    Room room = roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
    room.openNextSlot(slotComponent, clock);
  }

  @Override
  public void scheduleComingSlotFor(long roomId) {
    Room room = roomRepository.find(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
    UpcomingSlot upcomingSlot = room.findUpcomingSlot(slotComponent, clock);
    schedulerComponent.schedule(taskBuilder -> taskBuilder
      .executionTime(upcomingSlot.getCreationTime())
      .event(roomEventFactory.openNextSlotCommand(upcomingSlot.getRoomId())));
  }
}
