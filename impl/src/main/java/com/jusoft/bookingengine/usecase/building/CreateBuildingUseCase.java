package com.jusoft.bookingengine.usecase.building;

import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.ClubNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateBuildingUseCase {

    private final ClubManagerComponent clubManagerComponent;
    private final BuildingManagerComponent buildingManagerComponent;

    public BuildingView createBuildingFrom(CreateBuildingCommand command) {
        if (!clubManagerComponent.isAvailable(command.getClubId())) {
            throw new ClubNotFoundException(command.getClubId());
        }
        return buildingManagerComponent.create(command);
    }
}
