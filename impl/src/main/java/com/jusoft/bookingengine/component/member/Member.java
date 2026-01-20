package com.jusoft.bookingengine.component.member;

import com.jusoft.bookingengine.component.member.api.PersonalInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Data
class Member {

    private final long id;
    private final long userId;
    private final long clubId;
    private final PersonalInfo personalInfo;
}
