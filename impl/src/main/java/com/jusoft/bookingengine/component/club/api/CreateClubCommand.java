package com.jusoft.bookingengine.component.club.api;

import com.jusoft.bookingengine.publisher.Command;

public record CreateClubCommand(String name, String description, long adminId) implements Command {}
