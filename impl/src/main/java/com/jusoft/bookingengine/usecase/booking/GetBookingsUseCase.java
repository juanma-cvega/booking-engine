package com.jusoft.bookingengine.usecase.booking;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetBookingsUseCase {

    private final BookingManagerComponent bookingManagerComponent;

    public List<BookingView> getBookingsFor(long userId) {
        return bookingManagerComponent.findAllBy(userId);
    }
}
