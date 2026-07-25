package com.jusoft.bookingengine.component.club.api;

public enum Decision {
    ACCEPTED,
    DENIED;

    public static Decision from(String value) {
        return switch (value.toUpperCase()) {
            case "ACCEPTS", "ACCEPTED" -> ACCEPTED;
            case "DENIES", "DENIED" -> DENIED;
            default -> throw new IllegalArgumentException("Unknown decision: " + value);
        };
    }
}
