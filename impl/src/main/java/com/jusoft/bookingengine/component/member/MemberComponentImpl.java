package com.jusoft.bookingengine.component.member;

import com.jusoft.bookingengine.component.member.api.CreateMemberCommand;
import com.jusoft.bookingengine.component.member.api.MemberComponent;
import com.jusoft.bookingengine.component.member.api.MemberCreatedEvent;
import com.jusoft.bookingengine.component.member.api.MemberView;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MemberComponentImpl implements MemberComponent {

  private MemberFactory memberFactory;
  private MemberRepository repository;
  private MessagePublisher messagePublisher;

  @Override
  public MemberView createMember(CreateMemberCommand command) {
    Member member = memberFactory.createFrom(command);
    repository.save(member);
    messagePublisher.publish(new MemberCreatedEvent(member.getId(), member.getUserId(), member.getClubId()));
    return memberFactory.createFrom(member);
  }

  @Override
  public boolean isMemberOf(long clubId, long userId) {
    return repository.isMemberOf(clubId, userId);
  }
}
