package com.jusoft.bookingengine.usecase.authorization;

import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.CheckAuthorizationCommand;
import com.jusoft.bookingengine.component.authorization.api.Coordinates;
import com.jusoft.bookingengine.component.authorization.api.UnauthorisedException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthoriseUserUseCase {

  private final AuthorizationManagerComponent authorizationManagerComponent;

  public void isAuthorised(AuthoriseUserUseCaseCommand command) {
    boolean isAuthorised = authorizationManagerComponent.isAuthorised(CheckAuthorizationCommand.of(
      Coordinates.of(command.getBuildingId(), command.getRoomId(), command.getSlotCreationTime()),
      command.getClubId(),
      command.getUserId()
    ));
    if (!isAuthorised) {
      throw new UnauthorisedException(command.getUserId(), command.getClubId(), command.getBuildingId(), command.getRoomId());
    }
  }
}
