package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubAuthorizationException;
import com.jusoft.bookingengine.component.club.api.FindJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static org.assertj.core.api.Assertions.assertThat;

public class FindJoinRequestsUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private FindJoinRequestsUseCase findJoinRequestsUseCase;

  private Set<JoinRequest> joinRequestsFound;

  public FindJoinRequestsUseCaseStepDefinitions() {
    When("^admin (\\d+) looks for all join requests created", (Long adminId) ->
      joinRequestsFound = findJoinRequestsUseCase.findJoinRequests(new FindJoinRequestCommand(adminId, clubCreated.getId())));
    When("^user (\\d+) looks for all join requests created", (Long adminId) ->
      storeException(() -> joinRequestsFound = findJoinRequestsUseCase.findJoinRequests(new FindJoinRequestCommand(adminId, clubCreated.getId()))));
    Then("^the admin should be able to see the list of join requests containing requests for users$", (DataTable userIdsDataTable) -> {
      List<Long> userIds = userIdsDataTable.asList(Long.class);
      assertThat(joinRequestsFound).extracting("userId").hasSameElementsAs(userIds);
    });
    Then("^the user (\\d+) should receive a notification that he is not allowed to see the list$", (Long userId) -> {
      assertThat(exceptionThrown).isInstanceOf(ClubAuthorizationException.class);
      ClubAuthorizationException exception = (ClubAuthorizationException) exceptionThrown;
      assertThat(exception.getUserId()).isEqualTo(userId);
    });
  }


}
