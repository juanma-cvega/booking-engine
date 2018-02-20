package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.member.api.CreateMemberCommand;
import com.jusoft.bookingengine.component.member.api.MemberCreatedEvent;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.memberCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class CreateMemberUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private MemberManagerComponent memberManagerComponent;

  @Autowired
  private CreateMemberUseCase createMemberUseCase;

  public CreateMemberUseCaseStepDefinitions() {
    When("^the accepted join request for user (\\d+) is processed$", (Long userId) ->
      memberCreated = createMemberUseCase.addMemberToClubUseCase(new CreateMemberCommand(userId, clubCreated.getId())));
    Then("^the user (\\d+) should be a member of club$", (Long userId) ->
      assertThat(memberManagerComponent.isMemberOf(clubCreated.getId(), userId)).isTrue());
    Then("^a notification of a new membership for user (\\d+) has been created should be published$", (Long userId) -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(MemberCreatedEvent.class);
      MemberCreatedEvent event = (MemberCreatedEvent) messageCaptor.getValue();
      assertThat(event.getClubId()).isEqualTo(clubCreated.getId());
      assertThat(event.getUserId()).isEqualTo(userId);
      assertThat(event.getMemberId()).isEqualTo(memberCreated.getId());

    });
  }
}
