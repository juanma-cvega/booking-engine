package com.jusoft.bookingengine.controller.club;

import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.controller.club.api.ClubResource;
import com.jusoft.bookingengine.controller.club.api.JoinRequestResource;

class ClubResourceFactory {

    ClubResource createFrom(ClubView club) {
        return new ClubResource(club.id(), club.name(), club.description(), club.admins());
    }

    JoinRequestResource createFrom(JoinRequest joinRequest) {
        return new JoinRequestResource(joinRequest.id(), joinRequest.userId());
    }
}
