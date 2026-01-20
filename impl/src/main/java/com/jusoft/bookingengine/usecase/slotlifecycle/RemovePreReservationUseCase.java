package com.jusoft.bookingengine.usecase.slotlifecycle;

import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemovePreReservationUseCase {

    private final SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

    public void removePreReservationFrom(long roomId, ZonedDateTime slotStartTime) {
        slotLifeCycleManagerComponent.removePreReservation(roomId, slotStartTime);
    }
}
