package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddBuildingTagsToClubUseCase {

  private final AuthorizationManagerComponent authorizationManagerComponent;

  public void addBuildingTagsToClub(AddBuildingTagsToClubCommand command) {
    authorizationManagerComponent.addBuildingTagsToClub(command);
  }
}
