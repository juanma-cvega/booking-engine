package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubAuthorizationException;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.club.api.JoinRequestDeniedEvent;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.clubAdmin;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestCreated;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestsCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DenyJoinRequestUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private MemberManagerComponent memberManagerComponent;
  @Autowired
  private ClubManagerComponent clubManagerComponent;

  @Autowired
  private DenyJoinRequestUseCase denyJoinRequestUseCase;

  public DenyJoinRequestUseCaseStepDefinitions() {
    When("^admin (\\d+) denies the join request created by user (\\d+)$", (Long adminId, Long userId) -> {
      JoinRequest joinRequestForUser = joinRequestsCreated.stream()
        .filter(joinRequest -> joinRequest.getUserId() == userId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Unable to find join request for user %s", userId)));
      denyJoinRequestUseCase.denyJoinRequest(DenyJoinRequestCommand.of(joinRequestForUser.getId(), clubCreated.getId(), adminId));
    });
    When("^user (\\d+) denies the join request created by user (\\d+)$", (Long notAdminId, Long userId) -> {
      JoinRequest joinRequestForUser = joinRequestsCreated.stream()
        .filter(joinRequest -> joinRequest.getUserId() == userId)
        .findFirst()
        .orElseThrow(() -> new RuntimeException(String.format("Unable to find join request for user %s", userId)));
      storeException(() -> denyJoinRequestUseCase.denyJoinRequest(DenyJoinRequestCommand.of(joinRequestForUser.getId(), clubCreated.getId(), notAdminId)));
    });
    When("^admin (\\d+) denies the non existing join request (\\d+)$", (Long adminId, Long joinRequestId) ->
      storeException(() -> denyJoinRequestUseCase.denyJoinRequest(DenyJoinRequestCommand.of(joinRequestId, clubCreated.getId(), adminId))));
    Then("^a notification of a join request denied for user (\\d+) should be published$", (Long userId) -> {
      JoinRequestDeniedEvent event = verifyAndGetMessageOfType(JoinRequestDeniedEvent.class);
      assertThat(event.getClubId()).isEqualTo(clubCreated.getId());
      assertThat(event.getAccessRequestId()).isEqualTo(joinRequestCreated.getId());
      assertThat(event.getUserId()).isEqualTo(userId);
      assertThat(joinRequestCreated.getUserId()).isEqualTo(userId);
    });
    Then("^the user (.*) should be notified he has no rights to deny join requests$", (Long userId) -> {
      assertThat(exceptionThrown).isInstanceOf(ClubAuthorizationException.class);
      ClubAuthorizationException exception = (ClubAuthorizationException) exceptionThrown;
      assertThat(exception.getUserId()).isEqualTo(userId);
    });
    Then("^the club should have the join request for user (\\d+)$", (Long userId) ->
      assertThat(clubManagerComponent.findJoinRequests(clubCreated.getId(), clubAdmin)
        .stream()
        .anyMatch(joinRequest -> joinRequest.getUserId() == userId)).isTrue());
    Then("^a notification of a join request denied shouldn't be published$", () ->
      verifyZeroInteractions(messagePublisher));
  }
}
