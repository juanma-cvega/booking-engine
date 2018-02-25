package com.jusoft.bookingengine.component.authorization.api;

public interface AuthorizationComponent {
  void canReserveSlot(SlotCoordinates slotCoordinates, Runnable action);

  void canBid(SlotCoordinates slotCoordinates, Runnable action);
}
