package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.AcceptJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.FindJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.club.api.JoinRequestAcceptedEvent;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static com.jusoft.bookingengine.holder.DataHolder.clubAdmin;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestCreated;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestsCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class AcceptJoinRequestUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private MemberManagerComponent memberManagerComponent;
  @Autowired
  private ClubManagerComponent clubManagerComponent;

  @Autowired
  private AcceptJoinRequestUseCase acceptJoinRequestUseCase;

  public AcceptJoinRequestUseCaseStepDefinitions() {
    When("^admin (\\d+) accepts the join request created by user (\\d+)$", (Long adminId, Long userId) -> {
      JoinRequest joinRequestFromUser = joinRequestsCreated.stream()
        .filter(joinRequest -> joinRequest.getUserId() == userId)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("User does not have a join request created"));
      acceptJoinRequestUseCase.acceptJoinRequest(new AcceptJoinRequestCommand(joinRequestFromUser.getId(), clubCreated.getId(), adminId));
    });
    Then("^the club should not have the join request for user (\\d+) anymore$", (Long userId) -> {
      Set<JoinRequest> joinRequests = clubManagerComponent.findJoinRequests(new FindJoinRequestCommand(clubAdmin, clubCreated.getId()));
      assertThat(joinRequests.stream().anyMatch(joinRequest -> joinRequest.getUserId() == userId)).isFalse();
    });
    Then("^a notification of a join request accepted for user (\\d+) should be published$", (Long userId) -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(JoinRequestAcceptedEvent.class);
      JoinRequestAcceptedEvent event = (JoinRequestAcceptedEvent) messageCaptor.getValue();
      assertThat(event.getClubId()).isEqualTo(clubCreated.getId());
      assertThat(event.getAccessRequestId()).isEqualTo(joinRequestCreated.getId());
      assertThat(event.getUserId()).isEqualTo(userId);
      assertThat(joinRequestCreated.getUserId()).isEqualTo(userId);
    });
  }
}
