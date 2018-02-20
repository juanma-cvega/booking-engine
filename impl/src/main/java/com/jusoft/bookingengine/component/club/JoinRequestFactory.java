package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.JoinRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class JoinRequestFactory {

  private final Supplier<Long> idSupplier;

  JoinRequest createFrom(long userId) {
    return JoinRequest.of(idSupplier.get(), userId);
  }
}
