package com.jusoft.bookingengine.usecase.instructor;

import com.jusoft.bookingengine.component.instructor.api.InstructorManagerComponent;
import com.jusoft.bookingengine.component.instructor.api.InstructorView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.instructorCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class RegisterInstructorWithBuildingUseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private InstructorManagerComponent instructorManagerComponent;

  @Autowired
  private RegisterInstructorWithBuildingUseCase registerInstructorWithBuildingUseCase;

  public RegisterInstructorWithBuildingUseStepDefinitions() {
    When("^the instructor is registered with the building$", () -> {
      RegisterInstructorWithBuildingUseCaseCommand command = RegisterInstructorWithBuildingUseCaseCommand.of(buildingCreated.getId(), instructorCreated.getId());
      registerInstructorWithBuildingUseCase.registerOnBuilding(command);
    });
    Then("^the instructor should be registered with the building$", () -> {
      Optional<InstructorView> instructor = instructorManagerComponent.find(instructorCreated.getId());
      assertThat(instructor).isPresent();
      InstructorView instructorFound = instructor.get();
      assertThat(instructorFound.getBuildings()).contains(buildingCreated.getId());
    });
    And("^a notification of an instructor registered with the building should be published$", () -> {
      verifyAndGetExceptionThrown(Instr)
    });
  }
}
