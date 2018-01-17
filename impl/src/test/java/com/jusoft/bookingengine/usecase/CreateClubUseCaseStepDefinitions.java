package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.club.api.ClubCreatedEvent;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jusoft.bookingengine.fixture.ClubFixtures.CLUB_DESCRIPTION;
import static com.jusoft.bookingengine.holder.DataHolder.clubCreated;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class CreateClubUseCaseStepDefinitions extends AbstractUseCaseStepDefinitions {

  @Autowired
  private ClubComponent clubComponent;

  @Autowired
  private CreateClubUseCase createClubUseCase;

  public CreateClubUseCaseStepDefinitions() {
    When("^a club is created$", () ->
      clubCreated = createClubUseCase.createClubFrom(new CreateClubCommand(CLUB_DESCRIPTION)));
    Then("^the club should be stored$", () -> {
      ClubView clubFound = clubComponent.find(clubCreated.getId());
      assertThat(clubFound.getDescription()).isEqualTo(clubCreated.getDescription());
    });
    Then("^a notification of a created club should be published$", () -> {
      verify(messagePublisher).publish(messageCaptor.capture());
      assertThat(messageCaptor.getValue()).isInstanceOf(ClubCreatedEvent.class);
      ClubCreatedEvent clubCreatedEvent = (ClubCreatedEvent) messageCaptor.getValue();
      assertThat(clubCreatedEvent.getClubId()).isEqualTo(clubCreated.getId());
    });
  }
}
