package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.AcceptJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.ReviewJoinRequestCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewJoinRequestUseCase {

    private final ClubManagerComponent clubManagerComponent;

    public void review(ReviewJoinRequestCommand command) {
        switch (command.decision()) {
            case ACCEPTED ->
                    clubManagerComponent.acceptAccessRequest(
                            new AcceptJoinRequestCommand(
                                    command.joinRequestId(), command.clubId(), command.adminId()));
            case DENIED ->
                    clubManagerComponent.denyAccessRequest(
                            new DenyJoinRequestCommand(
                                    command.joinRequestId(), command.clubId(), command.adminId()));
        }
    }
}
