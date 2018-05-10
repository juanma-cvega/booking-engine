package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.jusoft.bookingengine.holder.DataHolder.buildingsCreated;
import static com.jusoft.bookingengine.holder.DataHolder.classesCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class FindClassesByBuildingUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClassManagerComponent classManagerComponent;

  @Autowired
  private FindClassesByBuildingUseCase findClassesByBuildingUseCase;

  private List<ClassView> classesForBuilding;

  public FindClassesByBuildingUseCaseStepDefinitions() {
    When("^the list of classes for the created building (\\d+) is looked up$", (Integer createdBuildingIndex) -> {
      BuildingView building = buildingsCreated.get(createdBuildingIndex - 1);
      classesForBuilding = findClassesByBuildingUseCase.findClassesBy(building.getId());
    });
    Then("^all created classes should be found$", () ->
      assertThat(classesForBuilding).hasSameSizeAs(classesCreated));
    Then("^only class created (\\d+) should be found$", (Integer createdClassIndex) -> {
      assertThat(classesForBuilding).hasSize(1);
      assertThat(classesCreated.get(createdClassIndex - 1)).isEqualTo(classesForBuilding.get(0));
    });
    Then("^the list of created classes found should be empty$", () -> {
      assertThat(classesForBuilding).isEmpty();
    });
  }
}
