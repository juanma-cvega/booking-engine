package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.room.api.SlotRequiredEvent;
import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategy;
import com.jusoft.bookingengine.strategy.slotcreation.api.SlotCreationStrategyRegistrar;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ScheduleNextSlotUseCase {

  private final RoomManagerComponent roomManagerComponent;
  private final SchedulerComponent schedulerComponent;
  private final SlotCreationStrategyRegistrar slotCreationStrategyRegistrar;

  //FIXME possible race condition. Two threads coming at the same time can see the number
  //FIXME of slots at max - 1 and decide to schedule a slot at the same time
  public void scheduleNextSlot(long roomId) {
    RoomView room = roomManagerComponent.find(roomId);
    SlotCreationStrategy strategy = slotCreationStrategyRegistrar.createStrategyWith(room.getSlotCreationConfigInfo());
    schedulerComponent.schedule(strategy.nextSlotCreationTimeFor(room.getId()), SlotRequiredEvent.of(roomId));
  }
}
