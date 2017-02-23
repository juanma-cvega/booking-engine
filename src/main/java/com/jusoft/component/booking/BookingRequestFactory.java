package com.jusoft.component.booking;

import java.time.LocalDateTime;
import java.util.function.Supplier;

class BookingRequestFactory {

    private final Supplier<LocalDateTime> instantSupplier;

    BookingRequestFactory(Supplier<LocalDateTime> instantSupplier) {
        this.instantSupplier = instantSupplier;
    }

    public CancelBookingCommand createFrom(Long userId, Long bookingId) {
        return new CancelBookingCommand(userId, bookingId, instantSupplier.get());
    }

    public CreateBookingCommand createFrom(CreateBookingRequest request) {
        return new CreateBookingCommand(request.getUserId(), request.getRoomId(), request.getSlotId(), instantSupplier.get());
    }
}
