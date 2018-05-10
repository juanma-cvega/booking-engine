package com.jusoft.bookingengine.usecase.classmanager;

import com.jusoft.bookingengine.component.classmanager.api.ClassManagerComponent;
import com.jusoft.bookingengine.component.classmanager.api.ClassView;
import com.jusoft.bookingengine.component.classmanager.api.RoomUnregisteredForClassEvent;
import com.jusoft.bookingengine.component.classmanager.api.UnregisterRoomCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.classCreated;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static org.assertj.core.api.Assertions.assertThat;

public class UnregisterRoomUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClassManagerComponent classManagerComponent;

  @Autowired
  private UnregisterRoomUseCase unregisterRoomUseCase;

  public UnregisterRoomUseCaseStepDefinitions(ClassManagerComponent classManagerComponent) {
    When("^the room is unregistered from the class$", () ->
      unregisterRoomUseCase.unregisterRoom(UnregisterRoomCommand.of(classCreated.getId(), roomCreated.getId())));
    Then("^the class should not have the room registered$", () -> {
      ClassView classFound = classManagerComponent.find(classCreated.getId());
      assertThat(classFound.getRoomsRegistered()).doesNotContain(roomCreated.getId());
    });
    Then("^a notification of the room being unregistered from the class should be published$", () -> {
      RoomUnregisteredForClassEvent event = verifyAndGetMessageOfType(RoomUnregisteredForClassEvent.class);
      assertThat(event.getClassId()).isEqualTo(classCreated.getId());
      assertThat(event.getRoomId()).isEqualTo(roomCreated.getId());
    });
  }
}
