package com.jusoft.bookingengine.component.member.api;

import java.util.Objects;

public record MemberView(long id, long userId, long clubId, PersonalInfo personalInfo) {
    public MemberView {
        Objects.requireNonNull(personalInfo, "personalInfo must not be null");
    }
}
