package com.jusoft.bookingengine.usecase.booking;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindBookingUseCase {

    private final BookingManagerComponent bookingManagerComponent;

    public BookingView find(long bookingId) {
        return bookingManagerComponent.find(bookingId);
    }
}
