package com.jusoft.bookingengine.component.slotlifecycle;

sealed interface NextSlotState permits AvailableState, InAuctionState, PreReservedState {}
