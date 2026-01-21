package com.jusoft.bookingengine.usecase.slot;

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

    // WARNING: in case a new option is added to schedule slots manually or by any other mean other
    // than the current
    // one,
    // there could exist a race condition where the strategy decides to create a new slot not
    // considering what other
    // thread could be doing at the same time
    public void scheduleNextSlot(long roomId) {
        RoomView room = roomManagerComponent.find(roomId);
        SlotCreationStrategy strategy =
                slotCreationStrategyRegistrar.createStrategyWith(room.slotCreationConfigInfo());
        schedulerComponent.schedule(
                strategy.nextSlotCreationTimeFor(room.id()), new SlotRequiredEvent(roomId));
    }
}
