package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.ReviewJoinRequestCommand;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewJoinRequestUseCase {

    private final ClubManagerComponent clubManagerComponent;

    public void review(ReviewJoinRequestCommand command) {
        clubManagerComponent.reviewAccessRequest(command);
    }
}
