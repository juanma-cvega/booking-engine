package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequestDeniedEvent;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.clubAdmin;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class DenyJoinRequestUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private MemberManagerComponent memberManagerComponent;
  @Autowired
  private ClubManagerComponent clubManagerComponent;

  @Autowired
  private DenyJoinRequestUseCase denyJoinRequestUseCase;

  public DenyJoinRequestUseCaseStepDefinitions() {
    When("^admin (\\d+) denies the join request created by user (\\d+)$", (Long adminId, Long userId) ->
      denyJoinRequestUseCase.denyJoinRequest(DenyJoinRequestCommand.of(joinRequestCreated.getId(), clubCreated.getId(), clubAdmin)));
    Then("^a notification of a join request denied for user (\\d+) should be published$", (Long userId) -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(JoinRequestDeniedEvent.class);
      JoinRequestDeniedEvent event = (JoinRequestDeniedEvent) messageCaptor.getValue();
      assertThat(event.getClubId()).isEqualTo(clubCreated.getId());
      assertThat(event.getAccessRequestId()).isEqualTo(joinRequestCreated.getId());
      assertThat(event.getUserId()).isEqualTo(userId);
      assertThat(joinRequestCreated.getUserId()).isEqualTo(userId);
    });
  }
}
