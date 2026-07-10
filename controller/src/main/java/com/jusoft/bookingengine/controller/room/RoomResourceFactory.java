package com.jusoft.bookingengine.controller.room;

import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.controller.room.api.OpenTimeResource;
import com.jusoft.bookingengine.controller.room.api.RoomResource;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;

class RoomResourceFactory {

    public RoomResource createFrom(RoomView roomView) {
        MaxNumberOfSlotsStrategyConfigInfo slotCreationConfig =
                (MaxNumberOfSlotsStrategyConfigInfo) roomView.slotCreationConfigInfo();
        return new RoomResource(
                roomView.id(),
                roomView.clubId(),
                roomView.buildingId(),
                roomView.slotDurationInMinutes(),
                slotCreationConfig.getMaxSlots(),
                roomView.openTimesPerDay().stream().map(this::createFrom).toList(),
                roomView.availableDays());
    }

    private OpenTimeResource createFrom(OpenTime openTime) {
        return new OpenTimeResource(
                openTime.getStartTime().getLocalTime().toString(),
                openTime.getEndTime().getLocalTime().toString());
    }
}
