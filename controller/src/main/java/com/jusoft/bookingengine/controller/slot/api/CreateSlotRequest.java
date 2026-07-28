package com.jusoft.bookingengine.controller.slot.api;

import jakarta.validation.constraints.NotNull;

public record CreateSlotRequest(@NotNull Long roomId) {}
