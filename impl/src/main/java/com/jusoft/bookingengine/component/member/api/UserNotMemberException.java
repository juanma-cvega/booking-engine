package com.jusoft.bookingengine.component.member.api;

import lombok.Getter;

@Getter
public class UserNotMemberException extends RuntimeException {

    private static final long serialVersionUID = -854473562174245477L;

    private static final String MESSAGE = "User %s not a member of club %s";

    private final long clubId;
    private final long userId;

    public UserNotMemberException(long userId, long clubId) {
        super(String.format(MESSAGE, userId, clubId));
        this.userId = userId;
        this.clubId = clubId;
    }
}
