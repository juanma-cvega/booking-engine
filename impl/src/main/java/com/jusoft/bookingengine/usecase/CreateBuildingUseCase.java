package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.building.api.BuildingComponent;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.club.api.ClubNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateBuildingUseCase {

  private final ClubComponent clubComponent;
  private final BuildingComponent buildingComponent;

  public BuildingView createBuildingFrom(CreateBuildingCommand command) {
    if (!clubComponent.isAvailable(command.getClubId())) {
      throw new ClubNotFoundException(command.getClubId());
    }
    return buildingComponent.create(command);
  }
}
