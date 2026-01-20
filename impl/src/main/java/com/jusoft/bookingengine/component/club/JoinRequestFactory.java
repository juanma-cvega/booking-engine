package com.jusoft.bookingengine.component.club;

import com.jusoft.bookingengine.component.club.api.JoinRequest;
import java.util.function.Supplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class JoinRequestFactory {

    private final Supplier<Long> idSupplier;

    JoinRequest createFrom(long userId) {
        return JoinRequest.of(idSupplier.get(), userId);
    }
}
