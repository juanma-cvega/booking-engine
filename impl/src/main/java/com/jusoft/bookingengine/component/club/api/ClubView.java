package com.jusoft.bookingengine.component.club.api;

import java.util.Objects;
import java.util.Set;

public record ClubView(long id, String name, String description, Set<Long> admins) {
    public ClubView {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(description, "description must not be null");
        Objects.requireNonNull(admins, "admins must not be null");
        admins = Set.copyOf(admins);
    }
}
