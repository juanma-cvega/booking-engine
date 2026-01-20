package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.CreateJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateJoinRequestUseCase {

    private final ClubManagerComponent clubManagerComponent;

    public JoinRequest createJoinRequest(CreateJoinRequestCommand command) {
        return clubManagerComponent.createJoinRequest(command);
    }
}
