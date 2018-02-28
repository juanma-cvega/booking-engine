package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewClubCreatedUseCase {

  private final AuthorizationManagerComponent authorizationManagerComponent;

  public void createClub(long clubId) {
    authorizationManagerComponent.createClub(clubId);
  }
}
