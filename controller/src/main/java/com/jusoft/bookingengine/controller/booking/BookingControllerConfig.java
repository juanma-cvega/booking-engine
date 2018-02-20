package com.jusoft.bookingengine.controller.booking;

import com.jusoft.bookingengine.component.booking.api.BookingManagerComponent;
import com.jusoft.bookingengine.controller.slot.SlotResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingControllerConfig {

  @Autowired
  private BookingManagerComponent bookingManagerComponent;

  @Autowired
  private SlotResourceFactory slotResourceFactory;

  @Bean
  public BookingControllerRest bookingComponentRest() {
    return new BookingControllerRest(bookingManagerComponent, bookingCommandFactory(), bookingResourceFactory());
  }

  private BookingCommandFactory bookingCommandFactory() {
    return new BookingCommandFactory();
  }

  private BookingResourceFactory bookingResourceFactory() {
    return new BookingResourceFactory(slotResourceFactory);
  }
}
