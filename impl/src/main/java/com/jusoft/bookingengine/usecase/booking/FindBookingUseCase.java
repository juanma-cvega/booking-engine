package com.jusoft.bookingengine.usecase.booking;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.booking.api.WrongBookingUserException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindBookingUseCase {

    private final BookingManagerComponent bookingManagerComponent;

    public BookingView find(long userId, long bookingId) {
        BookingView booking = bookingManagerComponent.find(bookingId);
        if (booking.userId() != userId) {
            throw new WrongBookingUserException(userId, booking.userId(), bookingId);
        }
        return booking;
    }
}
