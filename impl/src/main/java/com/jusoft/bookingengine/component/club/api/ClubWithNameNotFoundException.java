package com.jusoft.bookingengine.component.club.api;

import lombok.Getter;

@Getter
public class ClubWithNameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5785748568910803393L;

    private static final String MESSAGE = "Club with name %s not found";

    private final String name;

    public ClubWithNameNotFoundException(String name) {
        super(String.format(MESSAGE, name));
        this.name = name;
    }
}
