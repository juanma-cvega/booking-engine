package com.jusoft.bookingengine.component.club.api;

import java.util.Set;

public interface ClubManagerComponent {

  ClubView create(CreateClubCommand command);

  boolean isAvailable(long clubId);

  ClubView find(long clubId);

  ClubView findByName(String name);

  void acceptAccessRequest(AcceptJoinRequestCommand acceptJoinRequestCommand);

  void denyAccessRequest(DenyJoinRequestCommand acceptAccessRequestCommand);

  Set<JoinRequest> findJoinRequests(FindJoinRequestCommand command);

  JoinRequest createJoinRequest(CreateJoinRequestCommand command);
}
