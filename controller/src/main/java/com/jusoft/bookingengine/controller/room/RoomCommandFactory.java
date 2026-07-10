package com.jusoft.bookingengine.controller.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.timer.OpenTime;
import com.jusoft.bookingengine.controller.room.api.CreateRoomRequest;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import java.time.Clock;

class RoomCommandFactory {

    private final Clock clock;

    RoomCommandFactory(Clock clock) {
        this.clock = clock;
    }

    public CreateRoomCommand createFrom(CreateRoomRequest request) {
        return new CreateRoomCommand(
                request.buildingId(),
                new MaxNumberOfSlotsStrategyConfigInfo(request.maxSlots()),
                request.slotDurationInMinutes(),
                request.openTimes().stream()
                        .map(
                                openTime ->
                                        OpenTime.of(
                                                openTime.startTime(),
                                                openTime.endTime(),
                                                clock.getZone(),
                                                clock))
                        .toList(),
                request.availableDays());
    }
}
