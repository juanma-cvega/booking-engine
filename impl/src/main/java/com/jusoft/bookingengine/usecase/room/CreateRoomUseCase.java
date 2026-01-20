package com.jusoft.bookingengine.usecase.room;

import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.room.api.CreateRoomCommand;
import com.jusoft.bookingengine.component.room.api.RoomManagerComponent;
import com.jusoft.bookingengine.component.room.api.RoomView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateRoomUseCase {

    private final RoomManagerComponent roomManagerComponent;
    private final BuildingManagerComponent buildingManagerComponent;

    public RoomView createRoom(CreateRoomCommand createRoomCommand) {
        BuildingView building = buildingManagerComponent.find(createRoomCommand.getBuildingId());
        return roomManagerComponent.create(createRoomCommand, building.getClubId());
    }
}
