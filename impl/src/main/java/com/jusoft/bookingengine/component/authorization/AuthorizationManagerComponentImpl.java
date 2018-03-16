package com.jusoft.bookingengine.component.authorization;

import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AddBuildingTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToClubCommand;
import com.jusoft.bookingengine.component.authorization.api.AddRoomTagsToMemberCommand;
import com.jusoft.bookingengine.component.authorization.api.AuthorizationManagerComponent;
import com.jusoft.bookingengine.component.authorization.api.AuthorizeCommand;
import com.jusoft.bookingengine.component.authorization.api.ClubNotFoundException;
import com.jusoft.bookingengine.component.authorization.api.ClubView;
import com.jusoft.bookingengine.component.authorization.api.MemberNotFoundException;
import com.jusoft.bookingengine.component.authorization.api.MemberView;
import com.jusoft.bookingengine.component.authorization.api.ReplaceSlotAuthenticationConfigForRoomCommand;
import com.jusoft.bookingengine.component.authorization.api.SlotStatus;
import com.jusoft.bookingengine.component.authorization.api.Tag;
import com.jusoft.bookingengine.component.authorization.api.UnauthorisedException;
import com.jusoft.bookingengine.component.member.api.UserNotMemberException;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AuthorizationManagerComponentImpl implements AuthorizationManagerComponent {

  private final ClubRepository clubRepository;
  private final MemberRepository memberRepository;
  private final ClubFactory clubFactory;
  private final MemberFactory memberFactory;
  private final Clock clock;

  @Override
  public void authorise(AuthorizeCommand command) {
    Club clubFound = findClubOrFail(command.getClubId());
    SlotStatus slotStatus = clubFound.getSlotTypeFor(command.getBuildingId(), command.getRoomId(), command.getSlotCreationTime(), clock);
    Member memberFound = findMemberOrFail(command.getUserId(), command.getClubId());
    List<Tag> memberTags = memberFound.getTagsFor(command.getBuildingId(), command.getRoomId(), slotStatus);
    if (!clubFound.isAuthorisedFor(command.getBuildingId(), command.getRoomId(), memberTags, slotStatus)) {
      throw new UnauthorisedException(command.getUserId(), command.getClubId(), command.getBuildingId(), command.getRoomId());
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
    clubRepository.execute(command.getClubId(), club -> {
      club.addTagsToBuilding(command.getBuildingId(), command.getTags());
      return club;
    }, () -> new ClubNotFoundException(command.getClubId()));
  }

  @Override
  public void addBuildingTagsToMember(AddBuildingTagsToMemberCommand command) {
    memberRepository.execute(command.getMemberId(), member -> {
      member.addTagsToBuilding(command.getBuildingId(), command.getTags());
      return member;
    }, () -> new MemberNotFoundException(command.getMemberId()));
  }

  @Override
  public void addRoomTagsToClub(AddRoomTagsToClubCommand command) {
    clubRepository.execute(command.getClubId(), club -> {
      club.addTagsToRoom(command);
      return club;
    }, () -> new ClubNotFoundException(command.getClubId()));
  }

  @Override
  public void replaceSlotAuthenticationManagerForRoom(ReplaceSlotAuthenticationConfigForRoomCommand command) {
    clubRepository.execute(command.getClubId(), club -> {
      club.replaceSlotAuthorizationConfigForRoom(command);
      return club;
    }, () -> new ClubNotFoundException(command.getClubId()));
  }

  @Override
  public void addRoomTagsToMember(AddRoomTagsToMemberCommand command) {
    memberRepository.execute(command.getMemberId(), member -> {
      member.addTagsToRoom(command);
      return member;
    }, () -> new MemberNotFoundException(command.getMemberId()));
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
    return memberRepository.findByUserIdAndClubId(userId, clubId)
      .orElseThrow(() -> new UserNotMemberException(userId, clubId));
  }
}
