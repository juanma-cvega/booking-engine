package com.jusoft.bookingengine.usecase.club;

import static com.jusoft.bookingengine.holder.DataHolder.clubAdmin;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestCreated;
import static com.jusoft.bookingengine.holder.DataHolder.joinRequestsCreated;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.CreateJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.club.api.JoinRequestCreatedEvent;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import com.jusoft.bookingengine.publisher.Message;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateJoinRequestUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Autowired private ClubManagerComponent clubManagerComponent;

    @Autowired private CreateJoinRequestUseCase createJoinRequestUseCase;

    @When("^user (\\d+) issues a join request$")
    public void user_issues_a_join_request(Long userId) {
        joinRequestCreated =
                createJoinRequestUseCase.createJoinRequest(
                        CreateJoinRequestCommand.of(clubCreated.getId(), userId));
        joinRequestsCreated.add(joinRequestCreated);
    }

    @Then("^the club should have a? join requests? created for users?$")
    public void the_club_should_have_join_requests_created_for_users(DataTable userIdsDataTable) {
        List<Long> userIds =
                userIdsDataTable.row(0).stream().map(Long::parseLong).collect(Collectors.toList());
        Set<JoinRequest> joinRequests =
                clubManagerComponent.findJoinRequests(clubCreated.getId(), clubAdmin);
        assertThat(joinRequests).hasSize(userIds.size());
        assertThat(joinRequests).extracting("userId").hasSameElementsAs(userIds);
    }

    @Then("^a notification of a join request created should be published$")
    public void a_notification_of_a_join_request_created_should_be_published() {
        verify(messagePublisher).publish(messageCaptor.capture());
        assertThat(messageCaptor.getValue()).isInstanceOf(JoinRequestCreatedEvent.class);
        JoinRequestCreatedEvent event = (JoinRequestCreatedEvent) messageCaptor.getValue();
        assertThat(event.getClubId()).isEqualTo(clubCreated.getId());
        assertThat(event.getJoinRequestId()).isEqualTo(joinRequestCreated.getId());
    }

    @Then("^(\\d+) notifications of join requests created should be published$")
    public void notifications_of_join_requests_created_should_be_published(
            Integer notificationsCreated) {
        verify(messagePublisher, times(notificationsCreated)).publish(messageCaptor.capture());
        assertThat(messageCaptor.getAllValues()).isInstanceOf(List.class);
        List<Message> events = messageCaptor.getAllValues();
        assertThat(events).extracting("class").containsOnly(JoinRequestCreatedEvent.class);
        assertThat(events).hasSize(notificationsCreated);
        assertThat(events).extracting("clubId").containsOnly(clubCreated.getId());
        assertThat(events)
                .extracting("joinRequestId")
                .hasSameElementsAs(
                        joinRequestsCreated.stream().map(JoinRequest::getId).collect(toSet()));
    }
}
