package com.jusoft.bookingengine.component.authorization.api;

import java.util.Optional;

public interface AuthorizationManagerComponent {

  boolean authoriseMemberFor(CheckAuthorizationCommand checkAuthorizationCommand);

  void createClub(long clubId);

  void createMember(long memberId);

  void addBuildingTagsToClub(AddBuildingTagsToClubCommand addBuildingTagsToClubCommand);

  void addBuildingTagsToMember(AddBuildingTagsToMemberCommand addBuildingTagsToMemberCommand);

  void addRoomTagsToClub(AddRoomTagsToClubCommand addRoomTagsToClubCommand);

  void addRoomTagsToMember(AddRoomTagsToMemberCommand addClubRoomTagsCommand);

  Optional<ClubView> findClubBy(long clubId);

  Optional<MemberView> findMemberBy(long memberId);

}
