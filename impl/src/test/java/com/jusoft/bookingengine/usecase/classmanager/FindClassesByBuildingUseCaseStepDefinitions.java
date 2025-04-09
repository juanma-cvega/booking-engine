package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.building.api.BuildingView;
import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

  @When("^the list of classes for the created building (\\d+) is looked up$")
  public void the_list_of_classes_for_the_created_building_is_looked_up(Integer createdBuildingIndex) {
    BuildingView building = buildingsCreated.get(createdBuildingIndex - 1);
    classesForBuilding = findClassesByBuildingUseCase.findClassesBy(building.getId());
  }
  @Then("^all created classes should be found$")
  public void all_created_classes_should_be_found() {
    assertThat(classesForBuilding).hasSameSizeAs(classesCreated);
  }
  @Then("^only class created (\\d+) should be found$")
  public void only_class_created_should_be_found(Integer createdClassIndex) {
    assertThat(classesForBuilding).hasSize(1);
    assertThat(classesCreated.get(createdClassIndex - 1)).isEqualTo(classesForBuilding.get(0));
  }
  @Then("^the list of created classes found should be empty$")
  public void the_list_of_created_classes_found_should_be_empty() {
    assertThat(classesForBuilding).isEmpty();
  }
}
