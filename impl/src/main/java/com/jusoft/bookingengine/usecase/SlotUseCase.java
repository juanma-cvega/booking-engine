package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.scheduler.api.ScheduledEvent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import com.jusoft.bookingengine.component.slot.api.CreateSlotCommand;
import com.jusoft.bookingengine.component.slot.api.NoSlotsForRoomException;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.timer.OpenDate;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;

@AllArgsConstructor
public class SlotUseCase {

  private final RoomComponent roomComponent;
  private final SlotComponent slotComponent;
  private final MessagePublisher messagePublisher;
  private final Clock clock;

  public void openNextSlotFor(long roomId) {
    OpenDate nextSlotOpenDate = slotComponent.findLastCreatedFor(roomId)
      .map(slot -> roomComponent.findNextSlotOpenDate(slot.getEndDate(), roomId))
      .orElse(roomComponent.findFirstSlotOpenDate(roomId));

    slotComponent.create(new CreateSlotCommand(roomId, nextSlotOpenDate.getStartTime(), nextSlotOpenDate.getEndTime()), clock);
  }

  //FIXME add strategies to calculate the max number of slots
  public void scheduleNextSlot(long roomId) {
    int numberOfOpenSlots = slotComponent.findNumberOfSlotsOpenFor(roomId);
    ZonedDateTime nextSlotToFinishEndDate = slotComponent.findSlotInUseOrToStartFor(roomId)
      .orElseThrow(() -> new NoSlotsForRoomException(roomId)).getEndDate();

    ZonedDateTime nextSlotCreationTime = roomComponent.findNextSlotOpeningTime(roomId, numberOfOpenSlots, nextSlotToFinishEndDate);
    ScheduledEvent scheduleNextSlotEvent = new ScheduledEvent(new OpenNextSlotCommand(roomId), nextSlotCreationTime);
    messagePublisher.publish(scheduleNextSlotEvent);
  }
}
