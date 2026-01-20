package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.PreReservation;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddPreReservationUseCase {

    private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

    public void addPreReservationTo(long roomId, PreReservation preReservation) {
        slotLifeCycleManagerComponent.addPreReservation(roomId, preReservation);
    }
}
