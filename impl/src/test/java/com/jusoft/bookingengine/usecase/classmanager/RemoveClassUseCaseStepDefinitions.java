package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassIsStillRegisteredInRoomsException;
import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassNotFoundException;
import com.jusoft.bookingengine.component.classmanager.api.ClassRemovedEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class RemoveClassUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClassManagerComponent classManagerComponent;

  @Autowired
  private RemoveClassUseCase removeClassUseCase;

  @When("^the class is remove$")
  public void the_class_is_remove () {
    removeClassUseCase.removeClass(classCreated.getId());
  }
  @Then("^a class should be removed$")
  public void a_class_should_be_removed() {
    ClassNotFoundException exception = catchThrowableOfType(() ->
      classManagerComponent.find(classCreated.getId()), ClassNotFoundException.class);
    assertThat(exception.getClassId()).isEqualTo(classCreated.getId());
  }
  @Then("^a notification of a removed class should published$")
    public void a_notification_of_a_removed_class_should_published() {
    ClassRemovedEvent event = verifyAndGetMessageOfType(ClassRemovedEvent.class);
    assertThat(event.getClassId()).isEqualTo(classCreated.getId());
  }
  @When("^the class is tried to be removed$")
  public void the_class_is_tried_to_be_removed() {
    storeException(() -> removeClassUseCase.removeClass(classCreated.getId()));
  }
  @Then("^the admin should be notified the class cannot be removed$")
  public void the_admin_should_be_notified_the_class_cannot_be_removed() {
    ClassIsStillRegisteredInRoomsException event = verifyAndGetExceptionThrown(ClassIsStillRegisteredInRoomsException.class);
    assertThat(event.getClassId()).isEqualTo(classCreated.getId());
  }
  @Then("^the class should still be available$")
  public void the_class_should_still_be_available () {
    assertThatCode(() -> classManagerComponent.find(classCreated.getId())).doesNotThrowAnyException();
  }
}
