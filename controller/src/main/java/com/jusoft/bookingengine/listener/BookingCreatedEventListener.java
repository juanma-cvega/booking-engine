package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.publisher.message.BookingCreatedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
class BookingCreatedEventListener {

    @EventListener(BookingCreatedMessage.class)
    public void bookingCreated(BookingCreatedMessage event) {
        log.info(
                "BookingCreatedEvent consumed: bookingId={}, userId={}, slotId={}",
                event.getBookingId(),
                event.getUserId(),
                event.getUserId());
    }
}
