package com.jusoft.component.booking;


import com.jusoft.component.booking.api.CreateBookingCommand;
import com.jusoft.component.slot.Slot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookingFactory {

    private final Supplier<Long> idGenerator;
    private final Clock clock;

    Booking create(CreateBookingCommand createBookingCommand, Slot slot) {
        return new Booking(idGenerator.get(), createBookingCommand.getUserId(), ZonedDateTime.now(clock), slot);
    }
}
