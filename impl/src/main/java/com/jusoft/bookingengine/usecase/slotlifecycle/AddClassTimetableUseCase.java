package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetable;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddClassTimetableUseCase {

    private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

    public void addClassTimetableTo(long roomId, ClassTimetable classTimetable) {
        slotLifeCycleManagerComponent.addClassTimetableTo(roomId, classTimetable);
    }
}
