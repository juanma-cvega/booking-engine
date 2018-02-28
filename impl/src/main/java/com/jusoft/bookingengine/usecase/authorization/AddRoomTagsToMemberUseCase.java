package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddRoomTagsToMemberUseCase {

  private final AuthorizationManagerComponent authorizationManagerComponent;

  public void addRoomTagsToMember(AddRoomTagsToMemberCommand command) {
    authorizationManagerComponent.addRoomTagsToMember(command);
  }
}
