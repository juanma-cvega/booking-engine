package com.jusoft.bookingengine.controller.club.api;

import jakarta.validation.constraints.NotNull;

public record CreateClubRequest(@NotNull String name, String description, @NotNull Long adminId) {}
