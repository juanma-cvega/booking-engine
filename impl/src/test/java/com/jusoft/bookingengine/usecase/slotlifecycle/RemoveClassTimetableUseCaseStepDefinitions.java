package com.jusoft.bookingengine.usecase.slotlifecycle;

import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetableNotFoundException;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class RemoveClassTimetableUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

    @Autowired private RemoveClassTimetableUseCase removeClassTimetableUseCase;

    @When("^the class (\\d+) is removed from room (\\d+)$")
    public void the_class_is_removed_from_room(Long classId, Long roomId) {
        removeClassTimetableUseCase.removeClassTimetableFrom(roomId, classId);
    }

    @Then(
            "^a slot lifecycle manager for room (\\d+) should not contain a configuration for class (\\d+)$")
    public void a_slot_lifecycle_manager_for_room_should_not_contain_a_configuration_for_class(
            Long roomId, Long classId) {
        SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
        assertThat(managerView.getClassesTimetable().get(classId)).isNull();
    }

    @When("^the class (\\d+) is tried to be removed from room (\\d+)$")
    public void the_class_is_tried_to_be_removed_from_room(Long classId, Long roomId) {
        storeException(() -> removeClassTimetableUseCase.removeClassTimetableFrom(roomId, classId));
    }

    @Then(
            "^the admin should receive a notification the class (\\d+) for slot lifecycle manager for room (\\d+) does not exist$")
    public void
            the_admin_should_receive_a_notification_the_class_for_slot_lifecycle_manager_for_room_does_not_exist(
                    Long classId, Long roomId) {
        ClassTimetableNotFoundException exception =
                verifyAndGetExceptionThrown(ClassTimetableNotFoundException.class);
        assertThat(exception.getRoomId()).isEqualTo(roomId);
        assertThat(exception.getClassId()).isEqualTo(classId);
    }
}
