package com.jusoft.bookingengine.component.slot;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Map;

import static com.jusoft.bookingengine.component.slot.api.SlotState.AVAILABLE;
import static com.jusoft.bookingengine.component.slot.api.SlotState.CREATED;
import static com.jusoft.bookingengine.component.slot.api.SlotState.IN_AUCTION;
import static com.jusoft.bookingengine.component.slot.api.SlotState.RESERVED;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SlotStateFactory {

  private static final Map<Class<? extends SlotState>, com.jusoft.bookingengine.component.slot.api.SlotState> dictionary = ImmutableMap.of(
    CreatedSlotState.class, CREATED,
    AvailableSlotState.class, AVAILABLE,
    InAuctionState.class, IN_AUCTION,
    ReservedState.class, RESERVED
  );

  public com.jusoft.bookingengine.component.slot.api.SlotState getSlotStateFor(SlotState state) {
    return dictionary.get(state.getClass());
  }
}
