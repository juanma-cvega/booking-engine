package com.jusoft.bookingengine.controller.club;

import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import com.jusoft.bookingengine.component.club.api.CreateJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.Decision;
import com.jusoft.bookingengine.component.club.api.ReviewJoinRequestCommand;

class ClubCommandFactory {

    CreateClubCommand createClubCommandFrom(String name, String description, long adminId) {
        return new CreateClubCommand(name, description, adminId);
    }

    CreateJoinRequestCommand createJoinRequestCommandFrom(long clubId, long userId) {
        return new CreateJoinRequestCommand(clubId, userId);
    }

    ReviewJoinRequestCommand reviewJoinRequestCommandFrom(
            long joinRequestId,
            long clubId,
            long adminId,
            com.jusoft.bookingengine.controller.club.api.Decision decision) {
        Decision componentDecision =
                switch (decision) {
                    case ACCEPTED -> Decision.ACCEPTED;
                    case DENIED -> Decision.DENIED;
                };
        return new ReviewJoinRequestCommand(joinRequestId, clubId, adminId, componentDecision);
    }
}
