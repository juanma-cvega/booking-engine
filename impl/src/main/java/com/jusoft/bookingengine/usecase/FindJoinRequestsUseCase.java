package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.FindJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class FindJoinRequestsUseCase {

  private final ClubManagerComponent clubManagerComponent;

  public Set<JoinRequest> findJoinRequests(FindJoinRequestCommand command) {
    return clubManagerComponent.findJoinRequests(command);
  }
}
