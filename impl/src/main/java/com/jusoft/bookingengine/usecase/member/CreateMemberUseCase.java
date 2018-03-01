package com.jusoft.bookingengine.usecase.member;

import com.jusoft.bookingengine.component.member.api.CreateMemberCommand;
import com.jusoft.bookingengine.component.member.api.MemberManagerComponent;
import com.jusoft.bookingengine.component.member.api.MemberView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateMemberUseCase {

  private final MemberManagerComponent memberManagerComponent;

  public MemberView addMemberToClubUseCase(CreateMemberCommand command) {
    return memberManagerComponent.createMember(command);
  }
}
