package com.jusoft.bookingengine.usecase.club;

import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindJoinRequestsUseCase {

    private final ClubManagerComponent clubManagerComponent;

    public Set<JoinRequest> findJoinRequests(long clubId, long adminId) {
        return clubManagerComponent.findJoinRequests(clubId, adminId);
    }
}
