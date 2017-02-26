package com.jusoft.component.booking;


import com.jusoft.component.slot.Slot;

import java.time.LocalDateTime;
import java.util.function.Supplier;

class BookingFactory {

    private final Supplier<Long> idGenerator;
    private final Supplier<LocalDateTime> instantSupplier;

    BookingFactory(Supplier<Long> idGenerator, Supplier<LocalDateTime> instantSupplier) {
        this.idGenerator = idGenerator;
        this.instantSupplier = instantSupplier;
    }

    Booking create(CreateBookingCommand createBookingCommand, Slot slot) {
        return new Booking(idGenerator.get(), createBookingCommand.getUserId(), instantSupplier.get(), slot);
    }
}
