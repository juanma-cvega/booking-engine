package com.jusoft.bookingengine.component.member;

import com.jusoft.bookingengine.repository.Repository;

import java.util.Optional;

interface MemberRepository extends Repository {

  void save(Member member);

  boolean isMemberOf(long clubId, long userId);

  Optional<Member> find(long userId, long clubId);
}
