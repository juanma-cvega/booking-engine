package com.jusoft.bookingengine.usecase.slot;

import static com.jusoft.bookingengine.holder.DataHolder.buildingCreated;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.roomCreated;
import static com.jusoft.bookingengine.holder.DataHolder.slotCreated;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.component.authorization.api.UnauthorizedReservationException;
import com.jusoft.bookingengine.component.member.api.UserNotMemberException;
import com.jusoft.bookingengine.component.slot.api.SlotAlreadyReservedException;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotNotAvailableException;
import com.jusoft.bookingengine.component.slot.api.SlotNotOpenException;
import com.jusoft.bookingengine.component.slot.api.SlotReservedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotState;
import com.jusoft.bookingengine.component.slot.api.SlotView;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class ReserveSlotUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    private static final String TAG_ONLY_IN_CLUB = "TAG_ONLY_IN_CLUB";

    @Autowired private AuthorizationManagerComponent authorizationManagerComponent;

    @Autowired private SlotManagerComponent slotManagerComponent;

    @Autowired private ReserveSlotForPersonUseCase reserveSlotForPersonUseCase;

    @Given("^user (\\d+) is the member (.*) of the club created$")
    public void user_is_the_member_of_the_club_created(Long userId, Long memberId) {
        authorizationManagerComponent.createMember(memberId, userId, roomCreated.clubId());
    }

    @Given("^the room created requires authorization to use it$")
    public void the_room_created_requires_authorization_to_use_it() {
        authorizationManagerComponent.addRoomTagsToClub(
                AddRoomTagsToClubCommand.of(
                        clubCreated.id(),
                        buildingCreated.id(),
                        roomCreated.id(),
                        SlotStatus.NORMAL,
                        ImmutableList.of(Tag.of(TAG_ONLY_IN_CLUB))));
    }

    @Then("^the user (.*) should get a notification that he is not a member of the club$")
    public void the_user_should_get_a_notification_that_he_is_not_a_member_of_the_club(
            Long userId) {
        assertThat(exceptionThrown).isInstanceOf(UserNotMemberException.class);
        UserNotMemberException exception = (UserNotMemberException) exceptionThrown;
        assertThat(exception.getUserId()).isEqualTo(userId);
        assertThat(exception.getClubId()).isEqualTo(clubCreated.id());
    }
    ;

    @When("^the slot is reserved by user (\\d+)$")
    public void the_slot_is_reserved_by_user(Long userId) {
        reserveSlotForPersonUseCase.reserveSlotForPerson(slotCreated.id(), userId);
    }

    @When("^the user (\\d+) tries to reserve the slot$")
    public void the_user_tries_to_reserve_the_slot(Long userId) {
        storeException(
                () -> reserveSlotForPersonUseCase.reserveSlotForPerson(slotCreated.id(), userId));
    }

    @Then("^the slot should be reserved$")
    public void the_slot_should_be_reserved() {
        SlotView slot = slotManagerComponent.find(slotCreated.id());
        assertThat(slot.state()).isEqualTo(SlotState.RESERVED);
    }

    @Then("^a notification of a slot reserved by user (\\d+) should be published$")
    public void a_notification_of_a_slot_reserved_by_user_should_be_published(Long userId) {
        SlotReservedEvent event = verifyAndGetMessageOfType(SlotReservedEvent.class);
        assertThat(event.slotId()).isEqualTo(slotCreated.id());
        assertThat(event.slotUser().userId()).isEqualTo(userId);
    }

    @Then("^the user should get a notification that the slot is already reserved$")
    public void the_user_should_get_a_notification_that_the_slot_is_already_reserved() {
        assertThat(exceptionThrown).isInstanceOf(SlotAlreadyReservedException.class);
        SlotAlreadyReservedException exception = (SlotAlreadyReservedException) exceptionThrown;
        assertThat(exception.getSlotId()).isEqualTo(slotCreated.id());
    }

    @Then("^the user should be notified the slot is not available$")
    public void the_user_should_be_notified_the_slot_is_not_available() {
        assertThat(exceptionThrown).isInstanceOf(SlotNotAvailableException.class);
        SlotNotAvailableException exception = (SlotNotAvailableException) exceptionThrown;
        assertThat(exception.getSlotId()).isEqualTo(slotCreated.id());
    }

    @Then("^the user should get a notification that the slot is already started$")
    public void the_user_should_get_a_notification_that_the_slot_is_already_started() {
        assertThat(exceptionThrown).isInstanceOf(SlotNotOpenException.class);
        SlotNotOpenException exception = (SlotNotOpenException) exceptionThrown;
        assertThat(exception.getSlotId()).isEqualTo(slotCreated.id());
    }

    @Then(
            "^the user (\\d+) should receive a notification he is not authorized to use the room created$")
    public void the_user_should_receive_a_notification_he_is_not_authorized_to_use_the_room_created(
            Long userId) {
        assertThat(exceptionThrown).isNotNull();
        assertThat(exceptionThrown).isInstanceOf(UnauthorizedReservationException.class);
        UnauthorizedReservationException exception =
                (UnauthorizedReservationException) exceptionThrown;
        assertThat(exception.getBuildingId()).isEqualTo(roomCreated.buildingId());
        assertThat(exception.getClubId()).isEqualTo(roomCreated.clubId());
        assertThat(exception.getRoomId()).isEqualTo(roomCreated.id());
        assertThat(exception.getUserId()).isEqualTo(userId);
    }
}
