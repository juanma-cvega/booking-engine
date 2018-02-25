package com.jusoft.bookingengine.component.member;

import com.jusoft.bookingengine.component.slot.api.SlotType;

import java.util.function.Supplier;

public interface SlotRole extends Role {

  boolean satisfiesFor(SlotType type);

  @Override
  default Supplier<RuntimeException> roleException() {
    return () -> new RuntimeException("Slot exception");
  }
}
