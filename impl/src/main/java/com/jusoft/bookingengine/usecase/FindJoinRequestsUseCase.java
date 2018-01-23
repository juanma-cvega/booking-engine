package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubComponent;
import com.jusoft.bookingengine.component.club.api.FindJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class FindJoinRequestsUseCase {

  private final ClubComponent clubComponent;

  public Set<JoinRequest> findJoinRequests(FindJoinRequestCommand command) {
    return clubComponent.findJoinRequests(command);
  }
}
