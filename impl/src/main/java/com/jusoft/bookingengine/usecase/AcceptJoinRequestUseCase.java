package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.AcceptJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.ClubComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AcceptJoinRequestUseCase {

  private final ClubComponent clubComponent;

  public void acceptJoinRequest(AcceptJoinRequestCommand command) {
    clubComponent.acceptAccessRequest(command);
  }
}
