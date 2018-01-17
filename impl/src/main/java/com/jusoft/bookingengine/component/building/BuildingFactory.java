package com.jusoft.bookingengine.component.building;

import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.building.api.CreateBuildingCommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BuildingFactory {

  private final Supplier<Long> idSupplier;

  Building createFrom(CreateBuildingCommand command) {
    return new Building(
      idSupplier.get(),
      command.getClubId(),
      command.getAddress(),
      command.getDescription()
    );
  }

  public BuildingView createFrom(Building building) {
    return new BuildingView(
      building.getId(),
      building.getClubId(),
      building.getAddress(),
      building.getDescription()
    );
  }
}
