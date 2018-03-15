package com.jusoft.bookingengine.component.authorization.api;

import java.util.Optional;

public interface AuthorizationManagerComponent {

  boolean isAuthorised(CheckAuthorizationCommand checkAuthorizationCommand);

  void createClub(long clubId);

  void createMember(long memberId, long userId, long clubId);

  void addBuildingTagsToClub(AddBuildingTagsToClubCommand addBuildingTagsToClubCommand);

  void addBuildingTagsToMember(AddBuildingTagsToMemberCommand addBuildingTagsToMemberCommand);

  void addRoomTagsToClub(AddRoomTagsToClubCommand addRoomTagsToClubCommand);

  void replaceSlotAuthenticationManagerForRoom(ReplaceSlotAuthenticationConfigForRoomCommand command);

  void addRoomTagsToMember(AddRoomTagsToMemberCommand addClubRoomTagsCommand);

  Optional<ClubView> findClubBy(long clubId);

  Optional<MemberView> findMemberBy(long memberId);

}
