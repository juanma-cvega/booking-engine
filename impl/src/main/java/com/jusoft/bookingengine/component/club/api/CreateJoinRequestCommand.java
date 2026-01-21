package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;

public record CreateJoinRequestCommand(long clubId, long userId) implements Command {}
