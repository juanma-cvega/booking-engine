package com.jusoft.bookingengine.component.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AllSlotsAccessRole implements SlotRole {

  private static final AllSlotsAccessRole INSTANCE = new AllSlotsAccessRole();

  @Override
  public boolean satisfiesFor(long id) {
    return true;
  }

  public SlotRole getInstance() {
    return INSTANCE;
  }
}
