package com.jusoft.bookingengine.controller.building;

import com.jusoft.bookingengine.component.building.api.Address;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
import com.jusoft.bookingengine.controller.building.api.CreateBuildingRequest;

class BuildingCommandFactory {

    public CreateBuildingCommand createFrom(CreateBuildingRequest request) {
        return new CreateBuildingCommand(
                request.clubId(),
                Address.of(request.street(), request.zipCode(), request.city()),
                request.description());
    }
}
