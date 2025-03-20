package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class FindClassUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClassManagerComponent classManagerComponent;

  @Autowired
  private FindClassUseCase findClassUseCase;

  private ClassView classFound;

  @When("^the class is looked up$")
  public void the_class_is_looked_up() {
    classFound = findClassUseCase.findClass(classCreated.getId());
  }
  @Then("^a class should be found$")
  public void a_class_should_be_found() {
    assertThat(classFound).isNotNull();
    assertThat(classFound.getBuildingId()).isEqualTo(classCreated.getBuildingId());
    assertThat(classFound.getDescription()).isEqualTo(classCreated.getDescription());
    assertThat(classFound.getClassType()).isEqualTo(classCreated.getClassType());
    assertThat(classFound.getRoomsRegistered()).isEmpty();
    assertThat(classFound.getInstructorsId()).hasSameElementsAs(classCreated.getInstructorsId());
  }
}
