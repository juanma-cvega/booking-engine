package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubCreatedEvent;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.fixture.ClubFixtures.CLUB_DESCRIPTION;
import static com.jusoft.bookingengine.fixture.ClubFixtures.CLUB_NAME;
import static com.jusoft.bookingengine.fixture.CommonFixtures.USER_ID_1;
import static com.jusoft.bookingengine.holder.DataHolder.clubAdmin;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class CreateClubUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClubManagerComponent clubManagerComponent;

  @Autowired
  private CreateClubUseCase createClubUseCase;

  public CreateClubUseCaseStepDefinitions() {
    When("^a club is created$", () -> {
      clubCreated = createClubUseCase.createClubFrom(CreateClubCommand.of(CLUB_NAME, CLUB_DESCRIPTION, USER_ID_1));
      clubAdmin = USER_ID_1;
    });
    When("^a club is created by user (.*)$", (Long userId) -> {
      clubCreated = createClubUseCase.createClubFrom(CreateClubCommand.of(CLUB_NAME, CLUB_DESCRIPTION, userId));
      clubAdmin = userId;
    });
    When("^a club is created with name (.*)$", (String clubName) -> {
      clubCreated = createClubUseCase.createClubFrom(CreateClubCommand.of(clubName, CLUB_DESCRIPTION, USER_ID_1));
      clubAdmin = USER_ID_1;
    });
    When("^a club with name (.*) is created by user (.*)$", (String clubName, Long userId) -> {
      clubCreated = createClubUseCase.createClubFrom(CreateClubCommand.of(clubName, CLUB_DESCRIPTION, userId));
      clubAdmin = userId;
    });
    Then("^the club should be stored with user (.*) as admin$", (Long userId) -> {
      ClubView clubFound = clubManagerComponent.find(clubCreated.getId());
      assertThat(clubFound.getDescription()).isEqualTo(clubCreated.getDescription());
      assertThat(clubFound.getAdmins()).containsExactly(userId);
    });
    Then("^a notification of a created club should be published$", () -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(ClubCreatedEvent.class);
      ClubCreatedEvent clubCreatedEvent = (ClubCreatedEvent) messageCaptor.getValue();
      assertThat(clubCreatedEvent.getClubId()).isEqualTo(clubCreated.getId());
    });
  }
}
