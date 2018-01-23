package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.club.api.CreateJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateJoinRequestUseCase {

  private final ClubComponent clubComponent;

  public JoinRequest createJoinRequest(CreateJoinRequestCommand command) {
    return clubComponent.createJoinRequest(command);
  }
}
