package com.jusoft.bookingengine.component.club;

import com.google.common.collect.Sets;
import com.jusoft.bookingengine.component.club.api.AcceptJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.ClubAuthorizationException;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.club.api.JoinRequestNotFoundException;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.apache.commons.lang3.Validate;

@Data
class Club {

    private final long id;
    private final String name;
    private final String description;
    private final Set<Long>
            admins; // TODO should Member contain the role of the user? Would that remove this list?
    private final Set<JoinRequest> joinRequests; // TODO should JoinRequest be its own component??

    Club(
            long id,
            String name,
            String description,
            Set<Long> admins,
            Set<JoinRequest> joinRequests) {
        Validate.notNull(name);
        Validate.notEmpty(admins);
        Validate.notNull(joinRequests);
        this.id = id;
        this.name = name;
        this.description = description;
        this.admins = new HashSet<>(admins);
        this.joinRequests = new HashSet<>(joinRequests);
    }

    Club(long id, String name, String description, long admin) {
        Validate.notNull(name);
        this.id = id;
        this.name = name;
        this.description = description;
        this.admins = Sets.newHashSet(admin);
        this.joinRequests = new HashSet<>();
    }

    Set<Long> getAdmins() {
        return new HashSet<>(admins);
    }

    Set<JoinRequest> getJoinRequests() {
        return new HashSet<>(joinRequests);
    }

    JoinRequest acceptAccessRequest(AcceptJoinRequestCommand acceptJoinRequestCommand) {
        return removeAccessRequestIfAllowed(
                acceptJoinRequestCommand.adminId(), acceptJoinRequestCommand.joinRequestId());
    }

    JoinRequest denyAccessRequest(DenyJoinRequestCommand denyJoinRequestCommand) {
        return removeAccessRequestIfAllowed(
                denyJoinRequestCommand.adminId(), denyJoinRequestCommand.joinRequestId());
    }

    private JoinRequest removeAccessRequestIfAllowed(long userId, long accessRequestId) {
        if (!isAdmin(userId)) {
            throw new ClubAuthorizationException(userId, id);
        }
        JoinRequest joinRequest =
                joinRequests.stream()
                        .filter(request -> request.getId() == accessRequestId)
                        .findFirst()
                        .orElseThrow(() -> new JoinRequestNotFoundException(accessRequestId, id));
        joinRequests.remove(joinRequest);
        return joinRequest;
    }

    boolean isAdmin(long adminId) {
        return admins.contains(adminId);
    }

    void addJoinRequest(JoinRequest joinRequest) {
        joinRequests.add(joinRequest);
    }
}
