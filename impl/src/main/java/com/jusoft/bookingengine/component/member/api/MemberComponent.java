package com.jusoft.bookingengine.component.member.api;

public interface MemberComponent {

  MemberView createMember(CreateMemberCommand command);

  boolean isMemberOf(long clubId, long userId);

}
