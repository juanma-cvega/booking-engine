package com.jusoft.bookingengine.usecase.member;

import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.memberCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.jusoft.bookingengine.component.member.api.CreateMemberCommand;
import com.jusoft.bookingengine.component.member.api.MemberCreatedEvent;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateMemberUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private MemberManagerComponent memberManagerComponent;

    @Autowired private CreateMemberUseCase createMemberUseCase;

    @When("^the accepted join request for user (\\d+) is processed$")
    public void the_accepted_join_request_for_user_is_processed(Long userId) {
        memberCreated =
                createMemberUseCase.addMemberToClubUseCase(
                        CreateMemberCommand.of(userId, clubCreated.getId()));
    }

    @Then("^the user (\\d+) should be a member of club$")
    public void the_user_should_be_a_member_of_club(Long userId) {
        assertThat(memberManagerComponent.isMemberOf(clubCreated.getId(), userId)).isTrue();
    }

    @Then(
            "^a notification of a new membership for user (\\d+) has been created should be published$")
    public void a_notification_of_a_new_membership_for_user_has_been_created_should_be_published(
            Long userId) {
        verify(messagePublisher).publish(messageCaptor.capture());
        assertThat(messageCaptor.getValue()).isInstanceOf(MemberCreatedEvent.class);
        MemberCreatedEvent event = (MemberCreatedEvent) messageCaptor.getValue();
        assertThat(event.clubId()).isEqualTo(clubCreated.getId());
        assertThat(event.userId()).isEqualTo(userId);
        assertThat(event.memberId()).isEqualTo(memberCreated.getId());
    }
}
