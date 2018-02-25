package com.jusoft.bookingengine.component.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EarlyBirdSlotAccessRole implements SlotRole {

  private static final EarlyBirdSlotAccessRole INSTANCE = new EarlyBirdSlotAccessRole();

  @Override
  public boolean satisfiesFor(long id) {
    return true;
  }

  public SlotRole getInstance() {
    return INSTANCE;
  }
}
