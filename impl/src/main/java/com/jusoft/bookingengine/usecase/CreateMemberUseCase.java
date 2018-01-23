package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.member.api.CreateMemberCommand;
import com.jusoft.bookingengine.component.member.api.MemberComponent;
import com.jusoft.bookingengine.component.member.api.MemberView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateMemberUseCase {

  private final MemberComponent memberComponent;

  public MemberView addMemberToClubUseCase(CreateMemberCommand command) {
    return memberComponent.createMember(command);
  }
}
