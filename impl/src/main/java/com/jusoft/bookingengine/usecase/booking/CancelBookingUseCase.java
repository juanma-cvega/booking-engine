package com.jusoft.bookingengine.usecase.booking;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.component.booking.api.BookingView;
import com.jusoft.bookingengine.component.slot.api.SlotManagerComponent;
import com.jusoft.bookingengine.component.slot.api.SlotNotOpenException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CancelBookingUseCase {

    private final BookingManagerComponent bookingManagerComponent;
    private final SlotManagerComponent slotManagerComponent;

    public void cancel(long userId, long bookingId) {
        BookingView booking = bookingManagerComponent.find(bookingId);
        if (!slotManagerComponent.isSlotOpen(booking.slotId())) {
            throw new SlotNotOpenException(booking.slotId());
        }
        bookingManagerComponent.cancel(userId, bookingId);
    }
}
