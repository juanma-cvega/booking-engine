package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.component.classmanager.api.RoomUnregisteredForClassEvent;
import com.jusoft.bookingengine.component.classmanager.api.UnregisterRoomCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class UnregisterRoomUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClassManagerComponent classManagerComponent;

  @Autowired
  private UnregisterRoomUseCase unregisterRoomUseCase;

  @When("^the room is unregistered from the class$")
  public void the_room_is_unregistered_from_the_class() {
    unregisterRoomUseCase.unregisterRoom(UnregisterRoomCommand.of(classCreated.getId(), roomCreated.getId()));
  }
  @Then("^the class should not have the room registered$")
  public void the_class_should_not_have_the_room_registered() {
    ClassView classFound = classManagerComponent.find(classCreated.getId());
    assertThat(classFound.getRoomsRegistered()).doesNotContain(roomCreated.getId());
  }
  @Then("^a notification of the room being unregistered from the class should be published$")
  public void a_notification_of_the_room_being_unregistered_from_the_class_should_be_published() {
    RoomUnregisteredForClassEvent event = verifyAndGetMessageOfType(RoomUnregisteredForClassEvent.class);
    assertThat(event.getClassId()).isEqualTo(classCreated.getId());
    assertThat(event.getRoomId()).isEqualTo(roomCreated.getId());
  }
}
