package com.jusoft.bookingengine.usecase.club;

import static com.jusoft.bookingengine.holder.DataHolder.clubAdmin;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestCreated;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestsCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

import com.jusoft.bookingengine.component.club.api.AcceptJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.ClubAuthorizationException;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.club.api.JoinRequestAcceptedEvent;
import com.jusoft.bookingengine.component.club.api.JoinRequestNotFoundException;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class AcceptJoinRequestUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private MemberManagerComponent memberManagerComponent;

    @Autowired private ClubManagerComponent clubManagerComponent;

    @Autowired private AcceptJoinRequestUseCase acceptJoinRequestUseCase;

    @When("^admin (\\d+) accepts the join request created by user (\\d+)$")
    public void admin_accepts_the_join_request_created_by_user(Long adminId, Long userId) {
        JoinRequest joinRequestFromUser =
                joinRequestsCreated.stream()
                        .filter(joinRequest -> joinRequest.userId() == userId)
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "User does not have a join request created"));
        acceptJoinRequestUseCase.acceptJoinRequest(
                new AcceptJoinRequestCommand(joinRequestFromUser.id(), clubCreated.id(), adminId));
    }

    @When("^user (\\d+) accepts the join request created by user (\\d+)$")
    public void user_accepts_the_join_request_created_by_user(Long notAdminId, Long userId) {
        JoinRequest joinRequestForUser =
                joinRequestsCreated.stream()
                        .filter(joinRequest -> joinRequest.userId() == userId)
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                String.format(
                                                        "Unable to find join request for user %s",
                                                        userId)));
        storeException(
                () ->
                        acceptJoinRequestUseCase.acceptJoinRequest(
                                new AcceptJoinRequestCommand(
                                        joinRequestForUser.id(), clubCreated.id(), notAdminId)));
    }

    @When("^admin (\\d+) accepts the non existing join request (.*)$")
    public void admin_accepts_the_non_existing_join_request(Long adminId, Long joinRequestId) {
        storeException(
                () ->
                        acceptJoinRequestUseCase.acceptJoinRequest(
                                new AcceptJoinRequestCommand(
                                        joinRequestId, clubCreated.id(), adminId)));
    }

    @Then("^the club should not have the join request for user (\\d+) anymore$")
    public void the_club_should_not_have_the_join_request_for_user_anymore(Long userId) {
        Set<JoinRequest> joinRequests =
                clubManagerComponent.findJoinRequests(clubAdmin, clubCreated.id());
        assertThat(joinRequests.stream().anyMatch(joinRequest -> joinRequest.userId() == userId))
                .isFalse();
    }

    @Then("^a notification of a join request accepted for user (\\d+) should be published$")
    public void a_notification_of_a_join_request_accepted_for_user_should_be_published(
            Long userId) {
        JoinRequestAcceptedEvent event = verifyAndGetMessageOfType(JoinRequestAcceptedEvent.class);
        assertThat(event.clubId()).isEqualTo(clubCreated.id());
        assertThat(event.accessRequestId()).isEqualTo(joinRequestCreated.id());
        assertThat(event.userId()).isEqualTo(userId);
        assertThat(joinRequestCreated.userId()).isEqualTo(userId);
    }

    @Then("^the user (.*) should be notified he has no rights to accept join requests$")
    public void the_user_should_be_notified_he_has_no_rights_to_accept_join_requests(Long userId) {
        assertThat(exceptionThrown).isInstanceOf(ClubAuthorizationException.class);
        ClubAuthorizationException exception = (ClubAuthorizationException) exceptionThrown;
        assertThat(exception.getUserId()).isEqualTo(userId);
    }

    @Then("^a notification of a join request accepted shouldn't be published$")
    public void a_notification_of_a_join_request_accepted_should_not_be_published() {
        verifyNoInteractions(messagePublisher);
    }

    @Then("^the admin should be notified the join request (.*) does not exist$")
    public void the_admin_should_be_notified_the_join_request_does_not_exist(Long joinRequestId) {
        assertThat(exceptionThrown).isInstanceOf(JoinRequestNotFoundException.class);
        JoinRequestNotFoundException exception = (JoinRequestNotFoundException) exceptionThrown;
        assertThat(exception.getJoinRequestId()).isEqualTo(joinRequestId);
        assertThat(exception.getClubId()).isEqualTo(clubCreated.id());
    }
}
