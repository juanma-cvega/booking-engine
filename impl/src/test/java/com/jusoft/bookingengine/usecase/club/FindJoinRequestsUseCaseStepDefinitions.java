package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.ClubAuthorizationException;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

  @When("^admin (\\d+) looks for all join requests created")
  public void admin_looks_for_all_join_requests_created (Long adminId) {
    joinRequestsFound = findJoinRequestsUseCase.findJoinRequests(adminId, clubCreated.getId());
  }
  @When("^user (\\d+) looks for all join requests created")
  public void user_looks_for_all_join_requests_created(Long adminId) {
    storeException(() -> joinRequestsFound = findJoinRequestsUseCase.findJoinRequests(clubCreated.getId(), adminId));
  }
  @Then("^the admin should be able to see the list of join requests containing requests for users$")
  public void the_admin_should_be_able_to_see_the_list_of_join_requests_containing_requests_for_users (DataTable userIdsDataTable) {
    List<Long> userIds = userIdsDataTable.asList(Long.class);
    assertThat(joinRequestsFound).extracting("userId").hasSameElementsAs(userIds);
  }
  @Then("^the user (\\d+) should receive a notification that he is not allowed to see the list$")
  public void the_user_should_receive_a_notification_that_he_is_not_allowed_to_see_the_list (Long userId) {
    assertThat(exceptionThrown).isInstanceOf(ClubAuthorizationException.class);
    ClubAuthorizationException exception = (ClubAuthorizationException) exceptionThrown;
    assertThat(exception.getUserId()).isEqualTo(userId);
  }
}
