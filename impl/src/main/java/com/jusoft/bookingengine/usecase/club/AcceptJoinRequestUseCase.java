package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.AcceptJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AcceptJoinRequestUseCase {

    private final ClubManagerComponent clubManagerComponent;

    public void acceptJoinRequest(AcceptJoinRequestCommand command) {
        clubManagerComponent.acceptAccessRequest(command);
    }
}
