package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@Slf4j
public class BookingCreatedEventListener {

  @EventListener(BookingCreatedEvent.class)
  public void consume(BookingCreatedEvent event) {
    log.info("BookingCreatedEvent consumed: bookingId={}, userId={}, slotId={}",
      event.getBookingId(), event.getUserId(), event.getUserId());
  }
}
