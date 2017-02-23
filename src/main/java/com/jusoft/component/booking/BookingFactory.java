package com.jusoft.component.booking;


import java.util.function.Supplier;

class BookingFactory {

    private final Supplier<Long> idGenerator;

    BookingFactory(Supplier<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    Booking create(CreateBookingCommand createBookingCommand, Slot slot) {
        return new Booking(idGenerator.get(), createBookingCommand.getUserId(), createBookingCommand.getRequestTime(), slot);
    }
}
