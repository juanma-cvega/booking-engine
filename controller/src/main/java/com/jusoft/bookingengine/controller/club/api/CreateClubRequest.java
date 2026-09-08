package com.jusoft.bookingengine.controller.club.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateClubRequest(@NotBlank String name, String description, @NotNull Long adminId) {}
