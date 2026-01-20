package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateSlotLifeCycleManagerUseCase {

    private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

    public SlotLifeCycleManagerView createSlotLifeCycleManager(
            CreateSlotLifeCycleManagerCommand command) {
        return slotLifeCycleManagerComponent.createFrom(
                command.getRoomId(), command.getSlotsTimetable());
    }
}
