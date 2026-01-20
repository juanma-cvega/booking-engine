package com.jusoft.bookingengine.usecase.slotlifecycle;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetable;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetableNotValidException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ClassTimetableOverlappingException;
import com.jusoft.bookingengine.component.slotlifecycle.api.ReservedSlotsOfDay;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerComponent;
import com.jusoft.bookingengine.component.slotlifecycle.api.SlotLifeCycleManagerView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.holder.DataHolder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class AddClassTimetableUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private SlotLifeCycleManagerComponent slotLifeCycleManagerComponent;

    @Autowired private AddClassTimetableUseCase addClassTimetableUseCase;

    @When("^class (\\d+) is configured in slot lifecycle manager for room (\\d+) to use$")
    public void class_is_configured_in_slot_lifecycle_manager_for_room_to_use(
            Long classId, Long roomId, DataTable dataTable) {
        List<ReservedSlotsOfDay> reservedSlots = createDaysReservedSlotsFrom(dataTable);
        ClassTimetable classTimetable = ClassTimetable.of(classId, reservedSlots);
        addClassTimetableUseCase.addClassTimetableTo(roomId, classTimetable);
    }

    @Then("^slot lifecycle manager for room (\\d+) should contain class (\\d+) to use the room$")
    public void slot_lifecycle_manager_for_room_should_contain_class_to_use_the_room(
            Long roomId, Long classId, DataTable dataTable) {
        SlotLifeCycleManagerView managerView = slotLifeCycleManagerComponent.find(roomId);
        assertThat(managerView.getClassesTimetable().get(classId)).isNotNull();
        ClassTimetable classTimetable = managerView.getClassesTimetable().get(classId);
        assertThat(classTimetable.getClassId()).isEqualTo(classId);
        verifyClassTimetable(
                createDaysReservedSlotsFrom(dataTable), classTimetable.getReservedSlotsOfDays());
    }

    @When("^class (\\d+) is tried to be configured in room (\\d+) to use$")
    public void class_is_tried_to_be_configured_in_room_to_use(
            Long classId, Long roomId, DataTable dataTable) {
        List<ReservedSlotsOfDay> daysReservedSlots = createDaysReservedSlotsFrom(dataTable);
        ClassTimetable classTimetable = ClassTimetable.of(classId, daysReservedSlots);
        storeException(() -> addClassTimetableUseCase.addClassTimetableTo(roomId, classTimetable));
    }

    @Then(
            "^the admin should receive a notification the class information is not valid for class (\\d+) for room (\\d+)$")
    public void
            the_admin_should_receive_a_notification_the_class_information_is_not_valid_for_class_for_room(
                    Long classId, Long roomId, DataTable dataTable) {
        ClassTimetableNotValidException exception =
                verifyAndGetExceptionThrown(ClassTimetableNotValidException.class);
        assertThat(exception.getRoomId()).isEqualTo(roomId);
        assertThat(exception.getClassTimetable().getClassId()).isEqualTo(classId);
        verifyClassTimetable(
                createDaysReservedSlotsFrom(dataTable),
                exception.getClassTimetable().getReservedSlotsOfDays());
    }

    @Then(
            "^the admin should receive a notification the class information overlaps with an already reserved slot for class (\\d+) for room (\\d+)$")
    public void
            the_admin_should_receive_a_notification_the_class_information_overlaps_with_an_already_reserved_slot_for_class_for_room(
                    Long classId, Long roomId, DataTable dataTable) {
        ClassTimetableOverlappingException exception =
                verifyAndGetExceptionThrown(ClassTimetableOverlappingException.class);
        assertThat(exception.getRoomId()).isEqualTo(roomId);
        assertThat(exception.getClassTimetable().getClassId()).isEqualTo(classId);
        verifyClassTimetable(
                createDaysReservedSlotsFrom(dataTable),
                exception.getClassTimetable().getReservedSlotsOfDays());
    }

    private List<ReservedSlotsOfDay> createDaysReservedSlotsFrom(DataTable dataTable) {
        DataTable transposed = dataTable.transpose();
        List<List<String>> rows = transposed.asLists();

        return rows.stream()
                .skip(1) // Skip header row
                .map(
                        row -> {
                            java.time.DayOfWeek dayOfWeek = java.time.DayOfWeek.valueOf(row.get(0));
                            String slotsStartTime = row.get(1);
                            String zoneId = row.get(2);
                            return new DataHolder.ReservedSlotsOfDayHolder(
                                    dayOfWeek, slotsStartTime, zoneId);
                        })
                .map(reservedSlotsOfDayHolder -> reservedSlotsOfDayHolder.build(clock))
                .collect(toList());
    }

    private void verifyClassTimetable(
            List<ReservedSlotsOfDay> expectedDaysReservedSlot,
            List<ReservedSlotsOfDay> foundDaysReservedSlot) {
        assertThat(foundDaysReservedSlot)
                .extracting("dayOfWeek")
                .hasSameElementsAs(
                        expectedDaysReservedSlot.stream()
                                .map(ReservedSlotsOfDay::getDayOfWeek)
                                .collect(toList()));
        assertThat(foundDaysReservedSlot)
                .extracting("slotsStartTime")
                .hasSameElementsAs(
                        expectedDaysReservedSlot.stream()
                                .map(ReservedSlotsOfDay::getSlotsStartTime)
                                .collect(toList()));
    }
}
