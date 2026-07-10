package com.jusoft.bookingengine.controller.room;

import com.jusoft.bookingengine.component.room.api.RoomView;
import com.jusoft.bookingengine.controller.room.api.CreateRoomRequest;
import com.jusoft.bookingengine.controller.room.api.RoomResource;
import com.jusoft.bookingengine.usecase.room.CreateRoomUseCase;
import jakarta.validation.Valid;
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
    private final RoomCommandFactory roomCommandFactory;
    private final RoomResourceFactory roomResourceFactory;

    RoomControllerRest(
            CreateRoomUseCase createRoomUseCase,
            RoomCommandFactory roomCommandFactory,
            RoomResourceFactory roomResourceFactory) {
        this.createRoomUseCase = createRoomUseCase;
        this.roomCommandFactory = roomCommandFactory;
        this.roomResourceFactory = roomResourceFactory;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResource createRoom(@Valid @RequestBody CreateRoomRequest request) {
        RoomView room = createRoomUseCase.createRoom(roomCommandFactory.createFrom(request));
        return roomResourceFactory.createFrom(room);
    }
}
