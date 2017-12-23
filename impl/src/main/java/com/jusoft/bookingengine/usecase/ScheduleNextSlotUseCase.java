package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;

@AllArgsConstructor
public class ScheduleNextSlotUseCase {

  private final RoomComponent roomComponent;
  private final SlotComponent slotComponent;
  private final SchedulerComponent schedulerComponent;
  private final Clock clock;

  //FIXME add strategies to calculate the max number of slots
  //FIXME possible race condition. Two threads coming at the same time can see the number
  //FIXME of slots at max - 1 and decide to schedule a slot at the same time
  public void scheduleNextSlot(long roomId) {
    int numberOfOpenSlots = slotComponent.findNumberOfSlotsOpenFor(roomId);
    ZonedDateTime nextSlotToFinishEndDate = slotComponent.findSlotInUseOrToStartFor(roomId)
      .map(SlotView::getEndDate)
      .orElse(ZonedDateTime.now(clock));

    ZonedDateTime nextSlotCreationTime = roomComponent.findNextSlotCreationTime(roomId, numberOfOpenSlots, nextSlotToFinishEndDate);
    schedulerComponent.schedule(nextSlotCreationTime, new OpenNextSlotCommand(roomId));
  }
}
