package com.jusoft.bookingengine.controller.building;

import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.controller.building.api.BuildingResource;

class BuildingResourceFactory {

    public BuildingResource createFrom(BuildingView building) {
        return new BuildingResource(
                building.id(),
                building.clubId(),
                building.address().getStreet(),
                building.address().getZipCode(),
                building.address().getCity(),
                building.description());
    }
}
