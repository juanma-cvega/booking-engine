package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.AuthorizeCommand;
import com.jusoft.bookingengine.component.authorization.api.ChangeAccessToAuctionsCommand;
import com.jusoft.bookingengine.component.authorization.api.ClubNotFoundException;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import com.jusoft.bookingengine.component.authorization.api.MemberNotFoundException;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
import com.jusoft.bookingengine.component.authorization.api.ReplaceSlotAuthenticationConfigForRoomCommand;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.component.authorization.api.UnauthorizedBidException;
import com.jusoft.bookingengine.component.authorization.api.UnauthorizedReservationException;
import com.jusoft.bookingengine.component.member.api.UserNotMemberException;
import java.time.Clock;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationManagerComponentImpl implements AuthorizationManagerComponent {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final ClubFactory clubFactory;
    private final MemberFactory memberFactory;
    private final Clock clock;

    @Override
    public void authorizeReserveSlot(AuthorizeCommand command) {
        Member memberFound = findMemberOrFail(command.userId(), command.clubId());
        isAuthorized(command, memberFound);
    }

    @Override
    public void authorizeBidInAuction(AuthorizeCommand command) {
        Member memberFound = findMemberOrFail(command.userId(), command.clubId());
        isAuthorized(command, memberFound);
        if (!memberFound.canBidIn(command.buildingId(), command.roomId())) {
            throw new UnauthorizedBidException(
                    command.userId(), command.clubId(), command.buildingId(), command.roomId());
        }
    }

    private void isAuthorized(AuthorizeCommand command, Member memberFound) {
        Club clubFound = findClubOrFail(command.clubId());
        SlotStatus slotStatus =
                clubFound.getSlotTypeFor(
                        command.buildingId(), command.roomId(), command.slotCreationTime(), clock);
        List<Tag> memberTags =
                memberFound.getTagsFor(command.buildingId(), command.roomId(), slotStatus);
        if (!clubFound.isAuthorizedFor(
                command.buildingId(), command.roomId(), memberTags, slotStatus)) {
            throw new UnauthorizedReservationException(
                    command.userId(),
                    command.clubId(),
                    command.buildingId(),
                    command.roomId(),
                    slotStatus);
        }
    }

    @Override
    public void createClub(long clubId) {
        clubRepository.save(Club.of(clubId));
    }

    @Override
    public void createMember(long memberId, long userId, long clubId) {
        memberRepository.save(Member.of(memberId, userId, clubId));
    }

    @Override
    public void addBuildingTagsToClub(AddBuildingTagsToClubCommand command) {
        clubRepository.execute(
                command.clubId(),
                club -> club.addTagsToBuilding(command.buildingId(), command.tags()),
                () -> new ClubNotFoundException(command.clubId()));
    }

    @Override
    public void addBuildingTagsToMember(AddBuildingTagsToMemberCommand command) {
        memberRepository.execute(
                command.memberId(),
                member -> member.addTagsToBuilding(command.buildingId(), command.tags()),
                () -> new MemberNotFoundException(command.memberId()));
    }

    @Override
    public void addRoomTagsToClub(AddRoomTagsToClubCommand command) {
        clubRepository.execute(
                command.clubId(),
                club -> club.addTagsToRoom(command),
                () -> new ClubNotFoundException(command.clubId()));
    }

    @Override
    public void addAccessToAuctions(ChangeAccessToAuctionsCommand command) {
        memberRepository.execute(
                command.memberId(),
                member -> member.addAccessToAuctionsFor(command.buildingId(), command.roomId()),
                () -> new MemberNotFoundException(command.memberId()));
    }

    @Override
    public void removeAccessToAuctions(ChangeAccessToAuctionsCommand command) {
        memberRepository.execute(
                command.memberId(),
                member -> member.removeAccessToAuctionsFor(command.buildingId(), command.roomId()),
                () -> new MemberNotFoundException(command.memberId()));
    }

    @Override
    public void replaceSlotAuthenticationManagerForRoom(
            ReplaceSlotAuthenticationConfigForRoomCommand command) {
        clubRepository.execute(
                command.clubId(),
                club -> club.replaceSlotAuthorizationConfigForRoom(command),
                () -> new ClubNotFoundException(command.clubId()));
    }

    @Override
    public void addRoomTagsToMember(AddRoomTagsToMemberCommand command) {
        memberRepository.execute(
                command.memberId(),
                member -> member.addTagsToRoom(command),
                () -> new MemberNotFoundException(command.memberId()));
    }

    @Override
    public Optional<ClubView> findClubBy(long clubId) {
        return clubRepository.find(clubId).map(clubFactory::createFrom);
    }

    @Override
    public Optional<MemberView> findMemberBy(long memberId) {
        return memberRepository.find(memberId).map(memberFactory::createFrom);
    }

    private Club findClubOrFail(long clubId) {
        return clubRepository.find(clubId).orElseThrow(() -> new ClubNotFoundException(clubId));
    }

    private Member findMemberOrFail(long userId, long clubId) {
        return memberRepository
                .findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() -> new UserNotMemberException(userId, clubId));
    }
}
