package com.jusoft.bookingengine.component.member.api;

import com.jusoft.bookingengine.publisher.Command;

public record CreateMemberCommand(long userId, long clubId) implements Command {}
