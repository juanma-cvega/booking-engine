package com.jusoft.bookingengine.component.authorization;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

interface MemberRepository {

    Optional<Member> find(long memberId);

    void save(Member member);

    void execute(
            long memberId,
            UnaryOperator<Member> function,
            Supplier<RuntimeException> notFoundExceptionSupplier);

    Optional<Member> findByUserIdAndClubId(long userId, long clubId);
}
