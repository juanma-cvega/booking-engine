package com.jusoft.bookingengine.controller.club.api;

import jakarta.validation.constraints.NotNull;

public record ReviewJoinRequestRequest(@NotNull Long adminId) {}
