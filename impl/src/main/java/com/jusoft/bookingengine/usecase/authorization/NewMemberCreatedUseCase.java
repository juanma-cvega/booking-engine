package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewMemberCreatedUseCase {

  private final AuthorizationManagerComponent authorizationManagerComponent;

  public void createMember(long memberId, long userId, long clubId) {
    authorizationManagerComponent.createMember(memberId, userId, clubId);
  }
}
