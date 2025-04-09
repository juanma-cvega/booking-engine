package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.component.classmanager.api.RegisterRoomCommand;
import com.jusoft.bookingengine.component.classmanager.api.RoomRegisteredForClassEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class RegisterRoomUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClassManagerComponent classManagerComponent;

  @Autowired
  private RegisterRoomUseCase registerRoomUseCase;

  @When("^the room is registered to give the class$")
  public void the_room_is_registered_to_give_the_class() {
    registerRoomUseCase.registerRoom(RegisterRoomCommand.of(classCreated.getId(), roomCreated.getId()));
  }
  @Then("^the class should have the room registered$")
  public void the_class_should_have_the_room_registered () {
    ClassView classFound = classManagerComponent.find(classCreated.getId());
    assertThat(classFound.getRoomsRegistered()).contains(roomCreated.getId());
  }
  @Then("^a notification of the room being registered in the class should be published$")
  public void a_notification_of_the_room_being_registered_should_be_published() {
    RoomRegisteredForClassEvent event = verifyAndGetMessageOfType(RoomRegisteredForClassEvent.class);
    assertThat(event.getClassId()).isEqualTo(classCreated.getId());
    assertThat(event.getRoomId()).isEqualTo(roomCreated.getId());
  }
}
