package com.jusoft.bookingengine.component.member.api;

import com.jusoft.bookingengine.publisher.Message;

public record MemberCreatedEvent(long memberId, long userId, long clubId) implements Message {}
