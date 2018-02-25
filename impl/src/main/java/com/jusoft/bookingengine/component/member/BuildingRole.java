package com.jusoft.bookingengine.component.member;

import java.util.function.Supplier;

public interface BuildingRole extends Role {

  boolean satisfiesFor(long id);

  @Override
  default Supplier<RuntimeException> roleException() {
    return () -> new RuntimeException("Building exception");
  }
}
