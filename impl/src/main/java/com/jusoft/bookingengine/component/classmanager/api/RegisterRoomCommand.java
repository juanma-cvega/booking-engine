package com.jusoft.bookingengine.component.classmanager.api;

import com.jusoft.bookingengine.publisher.Command;

public record RegisterRoomCommand(long classId, long roomId) implements Command {}
