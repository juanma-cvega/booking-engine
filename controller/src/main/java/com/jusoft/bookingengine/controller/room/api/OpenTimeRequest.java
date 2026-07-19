package com.jusoft.bookingengine.controller.room.api;

import jakarta.validation.constraints.NotBlank;

public record OpenTimeRequest(@NotBlank String startTime, @NotBlank String endTime) {}
