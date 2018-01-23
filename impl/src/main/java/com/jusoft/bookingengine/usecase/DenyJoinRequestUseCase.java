package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DenyJoinRequestUseCase {

  private final ClubComponent clubComponent;

  public void denyJoinRequest(DenyJoinRequestCommand command) {
    clubComponent.denyAccessRequest(command);
  }
}
