package com.jusoft.bookingengine.component.authorization.api;

import lombok.Getter;

@Getter
public class ClubNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3440783410563182370L;

    private static final String MESSAGE = "Club %s not found";

    private final long clubId;

    public ClubNotFoundException(long clubId) {
        super(String.format(MESSAGE, clubId));
        this.clubId = clubId;
    }
}
