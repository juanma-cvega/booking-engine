package com.jusoft.bookingengine.controller.room;

import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.controller.room.api.CreateRoomRequest;
import com.jusoft.bookingengine.controller.room.api.OpenTimeResource;
import com.jusoft.bookingengine.controller.room.api.RoomResource;
import com.jusoft.bookingengine.strategy.slotcreation.api.MaxNumberOfSlotsStrategyConfigInfo;
import com.jusoft.bookingengine.usecase.room.CreateRoomUseCase;
import jakarta.validation.Valid;
import java.time.Clock;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rooms")
class RoomControllerRest {

    private final CreateRoomUseCase createRoomUseCase;
    private final Clock clock;

    RoomControllerRest(CreateRoomUseCase createRoomUseCase, Clock clock) {
        this.createRoomUseCase = createRoomUseCase;
        this.clock = clock;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResource createRoom(@Valid @RequestBody CreateRoomRequest request) {
        RoomView room =
                createRoomUseCase.createRoom(
                        new CreateRoomCommand(
                                request.buildingId(),
                                new MaxNumberOfSlotsStrategyConfigInfo(request.maxSlots()),
                                request.slotDurationInMinutes(),
                                request.toOpenTimes(clock),
                                request.availableDays()));
        MaxNumberOfSlotsStrategyConfigInfo slotCreationConfig =
                (MaxNumberOfSlotsStrategyConfigInfo) room.slotCreationConfigInfo();
        return new RoomResource(
                room.id(),
                room.clubId(),
                room.buildingId(),
                room.slotDurationInMinutes(),
                slotCreationConfig.getMaxSlots(),
                room.openTimesPerDay().stream().map(OpenTimeResource::from).toList(),
                room.availableDays());
    }
}
