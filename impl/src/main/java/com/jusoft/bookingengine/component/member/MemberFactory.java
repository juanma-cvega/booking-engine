package com.jusoft.bookingengine.component.member;

import com.jusoft.bookingengine.component.member.api.CreateMemberCommand;
import com.jusoft.bookingengine.component.member.api.MemberView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MemberFactory {

  private final Supplier<Long> idSupplier;

  public Member createFrom(CreateMemberCommand command) {
    return new Member(idSupplier.get(),
      command.getUserId(),
      command.getClubId(),
      null); //FIXME add personal info somehow. Is personal info shared from the users profile??
  }

  public MemberView createFrom(Member member) {
    return new MemberView(
      member.getId(),
      member.getUserId(),
      member.getClubId(),
      null);//FIXME;
  }
}
