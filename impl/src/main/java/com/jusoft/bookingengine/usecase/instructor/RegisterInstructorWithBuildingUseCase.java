package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.building.api.BuildingManagerComponent;
import com.jusoft.bookingengine.component.building.api.BuildingNotFoundException;
import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.RegisterWithBuildingCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterInstructorWithBuildingUseCase {

  private final InstructorManagerComponent instructorManagerComponent;
  private final BuildingManagerComponent buildingManagerComponent;

  public void registerOnBuilding(RegisterInstructorWithBuildingUseCaseCommand useCaseCommand) {
    BuildingView building = buildingManagerComponent.find(useCaseCommand.getBuildingId())
      .orElseThrow(() -> new BuildingNotFoundException(useCaseCommand.getBuildingId()));
    RegisterWithBuildingCommand command = RegisterWithBuildingCommand.of(building.getClubId(), building.getId(), useCaseCommand.getInstructorId());
    instructorManagerComponent.registerOnBuilding(command);
  }
}
