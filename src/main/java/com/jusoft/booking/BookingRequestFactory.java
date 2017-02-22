package com.jusoft.booking;

import java.time.LocalDateTime;
import java.util.function.Supplier;

class BookingRequestFactory {

    private final Supplier<LocalDateTime> instantSupplier;

    BookingRequestFactory(Supplier<LocalDateTime> instantSupplier) {
        this.instantSupplier = instantSupplier;
    }

    public CreateBookingRequest createFrom(Long userId, Long roomId, Long slotId) {
        return new CreateBookingRequest(userId, roomId, slotId, instantSupplier.get());
    }

    public CancelBookingRequest createFrom(Long userId, Long bookingId) {
        return new CancelBookingRequest(userId, bookingId, instantSupplier.get());
    }
}
