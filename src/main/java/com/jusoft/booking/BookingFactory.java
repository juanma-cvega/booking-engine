package com.jusoft.booking;


import java.util.function.Supplier;

class BookingFactory {

    private final Supplier<Long> idGenerator;

    BookingFactory(Supplier<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    Booking create(CreateBookingRequest createBookingRequest, Slot slot) {
        return new Booking(idGenerator.get(), createBookingRequest.getUserId(), createBookingRequest.getRequestTime(), slot);
    }
}
