package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateClubUseCase {

  private final ClubManagerComponent clubManagerComponent;

  public ClubView createClubFrom(CreateClubCommand command) {
    return clubManagerComponent.create(command);
  }
}
