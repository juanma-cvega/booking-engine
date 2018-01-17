package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateClubUseCase {

  private final ClubComponent clubComponent;

  public ClubView createClubFrom(CreateClubCommand command) {
    return clubComponent.create(command);
  }
}
