package com.jusoft.bookingengine.usecase.club;

import static com.jusoft.bookingengine.holder.DataHolder.clubAdmin;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestCreated;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestsCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

import com.jusoft.bookingengine.component.club.api.ClubAuthorizationException;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.club.api.JoinRequestDeniedEvent;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class DenyJoinRequestUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private MemberManagerComponent memberManagerComponent;

    @Autowired private ClubManagerComponent clubManagerComponent;

    @Autowired private DenyJoinRequestUseCase denyJoinRequestUseCase;

    @When("^admin (\\d+) denies the join request created by user (\\d+)$")
    public void admin_denies_the_join_request_created_by_user(Long adminId, Long userId) {
        JoinRequest joinRequestForUser =
                joinRequestsCreated.stream()
                        .filter(joinRequest -> joinRequest.getUserId() == userId)
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                String.format(
                                                        "Unable to find join request for user %s",
                                                        userId)));
        denyJoinRequestUseCase.denyJoinRequest(
                new DenyJoinRequestCommand(joinRequestForUser.getId(), clubCreated.id(), adminId));
    }

    @When("^user (\\d+) denies the join request created by user (\\d+)$")
    public void user_denies_the_join_request_created_by_user(Long notAdminId, Long userId) {
        JoinRequest joinRequestForUser =
                joinRequestsCreated.stream()
                        .filter(joinRequest -> joinRequest.getUserId() == userId)
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                String.format(
                                                        "Unable to find join request for user %s",
                                                        userId)));
        storeException(
                () ->
                        denyJoinRequestUseCase.denyJoinRequest(
                                new DenyJoinRequestCommand(
                                        joinRequestForUser.getId(), clubCreated.id(), notAdminId)));
    }

    @When("^admin (\\d+) denies the non existing join request (\\d+)$")
    public void admin_denies_the_non_existing_join_request(Long adminId, Long joinRequestId) {
        storeException(
                () ->
                        denyJoinRequestUseCase.denyJoinRequest(
                                new DenyJoinRequestCommand(
                                        joinRequestId, clubCreated.id(), adminId)));
    }

    @Then("^a notification of a join request denied for user (\\d+) should be published$")
    public void a_notification_of_a_join_request_denied_for_user_should_be_published(Long userId) {
        JoinRequestDeniedEvent event = verifyAndGetMessageOfType(JoinRequestDeniedEvent.class);
        assertThat(event.clubId()).isEqualTo(clubCreated.id());
        assertThat(event.accessRequestId()).isEqualTo(joinRequestCreated.getId());
        assertThat(event.userId()).isEqualTo(userId);
        assertThat(joinRequestCreated.getUserId()).isEqualTo(userId);
    }

    @Then("^the user (.*) should be notified he has no rights to deny join requests$")
    public void the_user_should_be_notified_he_has_no_rights_to_deny_join_requests(Long userId) {
        assertThat(exceptionThrown).isInstanceOf(ClubAuthorizationException.class);
        ClubAuthorizationException exception = (ClubAuthorizationException) exceptionThrown;
        assertThat(exception.getUserId()).isEqualTo(userId);
    }

    @Then("^the club should have the join request for user (\\d+)$")
    public void the_club_should_have_the_join_request_for_user(Long userId) {
        assertThat(
                        clubManagerComponent.findJoinRequests(clubCreated.id(), clubAdmin).stream()
                                .anyMatch(joinRequest -> joinRequest.getUserId() == userId))
                .isTrue();
    }

    @Then("^a notification of a join request denied shouldn't be published$")
    public void a_notification_of_a_join_request_denied_should_not_be_published() {
        verifyNoInteractions(messagePublisher);
    }
}
