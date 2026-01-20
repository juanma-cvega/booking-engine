package com.jusoft.bookingengine.component.member.api;

import lombok.Data;

@Data(staticConstructor = "of")
public class MemberView {

    private final long id;
    private final long userId;
    private final long clubId;
    private final PersonalInfo personalInfo;
}
