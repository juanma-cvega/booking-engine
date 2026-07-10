package com.jusoft.bookingengine.controller.club;

import com.jusoft.bookingengine.component.club.api.AcceptJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import com.jusoft.bookingengine.component.club.api.CreateJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;

class ClubCommandFactory {

    CreateClubCommand createClubCommandFrom(String name, String description, long adminId) {
        return new CreateClubCommand(name, description, adminId);
    }

    CreateJoinRequestCommand createJoinRequestCommandFrom(long clubId, long userId) {
        return new CreateJoinRequestCommand(clubId, userId);
    }

    AcceptJoinRequestCommand acceptJoinRequestCommandFrom(
            long joinRequestId, long clubId, long adminId) {
        return new AcceptJoinRequestCommand(joinRequestId, clubId, adminId);
    }

    DenyJoinRequestCommand denyJoinRequestCommandFrom(
            long joinRequestId, long clubId, long adminId) {
        return new DenyJoinRequestCommand(joinRequestId, clubId, adminId);
    }
}
