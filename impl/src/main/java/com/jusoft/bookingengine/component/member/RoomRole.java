package com.jusoft.bookingengine.component.member;

import java.util.function.Supplier;

public interface RoomRole extends Role {

  boolean satisfiesFor(long building, long roomId);

  @Override
  default Supplier<RuntimeException> roleException() {
    return () -> new RuntimeException("Room exception");
  }
}
