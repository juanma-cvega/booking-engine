package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.club.api.ClubCreatedEvent;
import com.jusoft.bookingengine.component.club.api.ClubNotFoundException;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClubComponentImpl implements ClubComponent {

  private final ClubFactory clubFactory;
  private final ClubRepository repository;
  private final MessagePublisher messagePublisher;

  @Override
  public ClubView create(CreateClubCommand command) {
    Club newClub = clubFactory.createFrom(command);
    repository.save(newClub);
    messagePublisher.publish(new ClubCreatedEvent(newClub.getId()));
    return clubFactory.createFrom(newClub);
  }

  @Override
  public boolean isAvailable(long clubId) {
    return repository.find(clubId).isPresent();
  }

  @Override
  public ClubView find(long clubId) {
    return clubFactory.createFrom(
      repository.find(clubId).orElseThrow(() -> new ClubNotFoundException(clubId)));
  }
}
