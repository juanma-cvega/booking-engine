package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class FindJoinRequestsUseCase {

  private final ClubManagerComponent clubManagerComponent;

  public Set<JoinRequest> findJoinRequests(long clubId, long adminId) {
    return clubManagerComponent.findJoinRequests(clubId, adminId);
  }
}
