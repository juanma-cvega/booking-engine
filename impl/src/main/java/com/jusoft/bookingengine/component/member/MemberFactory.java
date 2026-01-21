package com.jusoft.bookingengine.component.member;

import com.jusoft.bookingengine.component.member.api.CreateMemberCommand;
import com.jusoft.bookingengine.component.member.api.MemberView;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MemberFactory {

    private final Supplier<Long> idSupplier;

    public Member createFrom(CreateMemberCommand command) {
        return new Member(
                idSupplier.get(),
                command.getUserId(),
                command.getClubId(),
                null); // TODO add personal info somehow. Is personal info shared from the users
        // profile??
    }

    public MemberView createFrom(Member member) {
        return new MemberView(
                member.getId(),
                member.getUserId(),
                member.getClubId(),
                null); // TODO add personal info somehow. Is personal info shared from the users
        // profile??
    }
}
