package com.jusoft.bookingengine.component.club.api;

import java.util.Set;

public interface ClubManagerComponent {

    ClubView create(CreateClubCommand command);

    boolean isAvailable(long clubId);

    ClubView find(long clubId);

    ClubView findByName(String name);

    void reviewAccessRequest(ReviewJoinRequestCommand command);

    Set<JoinRequest> findJoinRequests(long clubId, long adminId);

    JoinRequest createJoinRequest(CreateJoinRequestCommand command);
}
