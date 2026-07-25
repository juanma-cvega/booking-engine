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
import com.jusoft.bookingengine.component.club.api.Decision;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.club.api.JoinRequestAcceptedEvent;
import com.jusoft.bookingengine.component.club.api.JoinRequestDeniedEvent;
import com.jusoft.bookingengine.component.club.api.JoinRequestNotFoundException;
import com.jusoft.bookingengine.component.club.api.ReviewJoinRequestCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

public class ReviewJoinRequestUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private ClubManagerComponent clubManagerComponent;

    @Autowired private ReviewJoinRequestUseCase reviewJoinRequestUseCase;

    @When("^admin (\\d+) (accepts|denies) the join request created by user (\\d+)$")
    public void admin_reviews_the_join_request_created_by_user(
            Long adminId, String decision, Long userId) {
        reviewJoinRequestUseCase.review(
                new ReviewJoinRequestCommand(
                        joinRequestFrom(userId).id(),
                        clubCreated.id(),
                        adminId,
                        Decision.from(decision)));
    }

    @When("^an unauthorized user (\\d+) (accepts|denies) the join request created by user (\\d+)$")
    public void an_unauthorized_user_reviews_the_join_request_created_by_user(
            Long unauthorizedUserId, String decision, Long userId) {
        storeException(
                () ->
                        reviewJoinRequestUseCase.review(
                                new ReviewJoinRequestCommand(
                                        joinRequestFrom(userId).id(),
                                        clubCreated.id(),
                                        unauthorizedUserId,
                                        Decision.from(decision))));
    }

    @When("^admin (\\d+) (accepts|denies) the non existing join request (\\d+)$")
    public void admin_reviews_the_non_existing_join_request(
            Long adminId, String decision, Long joinRequestId) {
        storeException(
                () ->
                        reviewJoinRequestUseCase.review(
                                new ReviewJoinRequestCommand(
                                        joinRequestId,
                                        clubCreated.id(),
                                        adminId,
                                        Decision.from(decision))));
    }

    @Then("^the club should not have the join request for user (\\d+) anymore$")
    public void the_club_should_not_have_the_join_request_for_user_anymore(Long userId) {
        Set<JoinRequest> joinRequests =
                clubManagerComponent.findJoinRequests(clubCreated.id(), clubAdmin);
        assertThat(joinRequests.stream().anyMatch(joinRequest -> joinRequest.userId() == userId))
                .isFalse();
    }

    @Then("^the club should have the join request for user (\\d+)$")
    public void the_club_should_have_the_join_request_for_user(Long userId) {
        assertThat(
                        clubManagerComponent.findJoinRequests(clubCreated.id(), clubAdmin).stream()
                                .anyMatch(joinRequest -> joinRequest.userId() == userId))
                .isTrue();
    }

    @Then(
            "^a notification of a join request (accepted|denied) for user (\\d+) should be published$")
    public void a_notification_of_the_review_should_be_published(String decision, Long userId) {
        long clubId;
        long accessRequestId;
        long notifiedUserId;
        if (Decision.from(decision) == Decision.ACCEPTED) {
            JoinRequestAcceptedEvent event =
                    verifyAndGetMessageOfType(JoinRequestAcceptedEvent.class);
            clubId = event.clubId();
            accessRequestId = event.accessRequestId();
            notifiedUserId = event.userId();
        } else {
            JoinRequestDeniedEvent event = verifyAndGetMessageOfType(JoinRequestDeniedEvent.class);
            clubId = event.clubId();
            accessRequestId = event.accessRequestId();
            notifiedUserId = event.userId();
        }
        assertThat(clubId).isEqualTo(clubCreated.id());
        assertThat(accessRequestId).isEqualTo(joinRequestCreated.id());
        assertThat(notifiedUserId).isEqualTo(userId);
        assertThat(joinRequestCreated.userId()).isEqualTo(userId);
    }

    @Then("^a notification of a join request (?:accepted|denied) shouldn't be published$")
    public void a_notification_of_the_review_should_not_be_published() {
        verifyNoInteractions(messagePublisher);
    }

    @Then("^the user (\\d+) should be notified he has no rights to (?:accept|deny) join requests$")
    public void the_user_should_be_notified_he_has_no_rights_to_review_join_requests(Long userId) {
        assertThat(exceptionThrown).isInstanceOf(ClubAuthorizationException.class);
        ClubAuthorizationException exception = (ClubAuthorizationException) exceptionThrown;
        assertThat(exception.getUserId()).isEqualTo(userId);
    }

    @Then("^the admin should be notified the join request (\\d+) does not exist$")
    public void the_admin_should_be_notified_the_join_request_does_not_exist(Long joinRequestId) {
        assertThat(exceptionThrown).isInstanceOf(JoinRequestNotFoundException.class);
        JoinRequestNotFoundException exception = (JoinRequestNotFoundException) exceptionThrown;
        assertThat(exception.getJoinRequestId()).isEqualTo(joinRequestId);
        assertThat(exception.getClubId()).isEqualTo(clubCreated.id());
    }

    private static JoinRequest joinRequestFrom(long userId) {
        return joinRequestsCreated.stream()
                .filter(joinRequest -> joinRequest.userId() == userId)
                .findFirst()
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        String.format(
                                                "Unable to find join request for user %s",
                                                userId)));
    }
}
