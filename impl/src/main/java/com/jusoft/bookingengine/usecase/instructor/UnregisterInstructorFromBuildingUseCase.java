package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.UnregisterFromBuildingCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnregisterInstructorFromBuildingUseCase {

  private final InstructorManagerComponent instructorManagerComponent;

  public void unregisterFromBuilding(UnregisterFromBuildingCommand command) {
    instructorManagerComponent.unregisterFromBuilding(command);
  }
}
