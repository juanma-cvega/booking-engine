package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DenyJoinRequestUseCase {

  private final ClubManagerComponent clubManagerComponent;

  public void denyJoinRequest(DenyJoinRequestCommand command) {
    clubManagerComponent.denyAccessRequest(command);
  }
}
