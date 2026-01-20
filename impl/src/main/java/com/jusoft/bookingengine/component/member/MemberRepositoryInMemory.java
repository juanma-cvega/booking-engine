package com.jusoft.bookingengine.component.member;

import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

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
}
