package com.jusoft.bookingengine.controller.club.api;

import java.util.Set;

public record ClubResource(long clubId, String name, String description, Set<Long> admins) {}
