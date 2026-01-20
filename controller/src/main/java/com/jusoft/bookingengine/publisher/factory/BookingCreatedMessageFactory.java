package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.publisher.message.BookingCreatedMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookingCreatedMessageFactory
        implements MessageFactory<BookingCreatedEvent, BookingCreatedMessage> {
    @Override
    public BookingCreatedMessage createFrom(BookingCreatedEvent message) {
        return BookingCreatedMessage.of(
                message.getBookingId(), message.getUserId(), message.getSlotId());
    }
}
