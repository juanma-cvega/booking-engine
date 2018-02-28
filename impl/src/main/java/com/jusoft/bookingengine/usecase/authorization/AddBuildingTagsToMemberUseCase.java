package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddBuildingTagsToMemberUseCase {

  private final AuthorizationManagerComponent authorizationManagerComponent;

  public void addBuildingTagsToMember(AddBuildingTagsToMemberCommand command) {
    authorizationManagerComponent.addBuildingTagsToMember(command);
  }
}
