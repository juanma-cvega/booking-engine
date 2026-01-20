package com.jusoft.bookingengine.component.building;

import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BuildingFactory {

    private final Supplier<Long> idSupplier;

    Building createFrom(CreateBuildingCommand command) {
        return new Building(
                idSupplier.get(),
                command.getClubId(),
                command.getAddress(),
                command.getDescription());
    }

    public BuildingView createFrom(Building building) {
        return BuildingView.of(
                building.getId(),
                building.getClubId(),
                building.getAddress(),
                building.getDescription());
    }
}
