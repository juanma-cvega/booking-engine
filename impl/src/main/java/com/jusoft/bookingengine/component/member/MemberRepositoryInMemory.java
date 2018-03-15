package com.jusoft.bookingengine.component.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MemberRepositoryInMemory implements MemberRepository {

  private final Map<Long, Member> store;

  @Override
  public void save(Member member) {
    store.put(member.getId(), member);
  }

  @Override
  public boolean isMemberOf(long clubId, long userId) {
    return store.values().stream()
      .filter(member -> member.getUserId() == userId)
      .anyMatch(member -> member.getClubId() == clubId);
  }

  @Override
  public Optional<Member> findByUserAndClub(long userId, long clubId) {
    return store.values().stream()
      .filter(member -> member.getClubId() == clubId)
      .filter(member -> member.getUserId() == userId)
      .findFirst();
  }
}
