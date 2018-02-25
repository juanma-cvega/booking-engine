package com.jusoft.bookingengine.component.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalSlotAccessRole implements SlotRole {

  private static final NormalSlotAccessRole INSTANCE = new NormalSlotAccessRole();

  @Override
  public boolean satisfiesFor(long id) {
    return true;
  }

  public SlotRole getInstance() {
    return INSTANCE;
  }
}
