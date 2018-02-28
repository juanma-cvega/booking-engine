package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.CheckAuthorizationCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthoriseMemberUseCase {

  private final AuthorizationManagerComponent authorizationManagerComponent;

  public boolean authoriseMember(CheckAuthorizationCommand command) {
    return authorizationManagerComponent.authoriseMemberFor(command);
  }
}
