package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.AcceptJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.ClubAuthorizationException;
import com.jusoft.bookingengine.component.club.api.ClubCreatedEvent;
import com.jusoft.bookingengine.component.club.api.ClubManagerComponent;
import com.jusoft.bookingengine.component.club.api.ClubNotFoundException;
import com.jusoft.bookingengine.component.club.api.ClubView;
import com.jusoft.bookingengine.component.club.api.ClubWithNameNotFoundException;
import com.jusoft.bookingengine.component.club.api.CreateClubCommand;
import com.jusoft.bookingengine.component.club.api.CreateJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.DenyJoinRequestCommand;
import com.jusoft.bookingengine.component.club.api.JoinRequest;
import com.jusoft.bookingengine.component.club.api.JoinRequestAcceptedEvent;
import com.jusoft.bookingengine.component.club.api.JoinRequestCreatedEvent;
import com.jusoft.bookingengine.component.club.api.JoinRequestDeniedEvent;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClubManagerComponentImpl implements ClubManagerComponent {

    private final ClubFactory clubFactory;
    private final JoinRequestFactory joinRequestFactory;
    private final ClubRepository repository;
    private final MessagePublisher messagePublisher;

    @Override
    public ClubView create(CreateClubCommand command) {
        Club newClub = clubFactory.createFrom(command);
        repository.save(newClub);
        messagePublisher.publish(new ClubCreatedEvent(newClub.getId()));
        return clubFactory.createFrom(newClub);
    }

    @Override
    public boolean isAvailable(long clubId) {
        return repository.find(clubId).isPresent();
    }

    @Override
    public ClubView find(long clubId) {
        return clubFactory.createFrom(findBy(clubId));
    }

    @Override
    public ClubView findByName(String name) {
        return clubFactory.createFrom(
                repository.findBy(name).orElseThrow(() -> new ClubWithNameNotFoundException(name)));
    }

    @Override
    public void acceptAccessRequest(AcceptJoinRequestCommand command) {
        JoinRequest joinRequest =
                repository.removeJoinRequest(
                        command.getClubId(),
                        club -> club.acceptAccessRequest(command),
                        () -> new ClubNotFoundException(command.getClubId()));
        messagePublisher.publish(
                new JoinRequestAcceptedEvent(
                        command.getJoinRequestId(), joinRequest.getUserId(), command.getClubId()));
    }

    @Override
    public void denyAccessRequest(DenyJoinRequestCommand command) {
        JoinRequest joinRequest =
                repository.removeJoinRequest(
                        command.getClubId(),
                        club -> club.denyAccessRequest(command),
                        () -> new ClubNotFoundException(command.getClubId()));
        messagePublisher.publish(
                new JoinRequestDeniedEvent(
                        command.getJoinRequestId(), joinRequest.getUserId(), command.getClubId()));
    }

    @Override
    public Set<JoinRequest> findJoinRequests(long clubId, long adminId) {
        Club club = findBy(clubId);
        if (!club.isAdmin(adminId)) {
            throw new ClubAuthorizationException(adminId, clubId);
        }
        return club.getJoinRequests();
    }

    @Override
    public JoinRequest createJoinRequest(CreateJoinRequestCommand command) {
        JoinRequest joinRequest = joinRequestFactory.createFrom(command.getUserId());
        repository.addJoinRequest(
                command.getClubId(),
                club -> {
                    club.addJoinRequest(joinRequest);
                    return club;
                },
                () -> new ClubNotFoundException(command.getClubId()));
        messagePublisher.publish(
                new JoinRequestCreatedEvent(joinRequest.getId(), command.getClubId()));
        return joinRequest;
    }

    private Club findBy(long clubId) {
        return repository.find(clubId).orElseThrow(() -> new ClubNotFoundException(clubId));
    }
}
