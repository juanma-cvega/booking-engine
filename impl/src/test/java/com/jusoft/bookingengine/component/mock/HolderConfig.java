package com.jusoft.bookingengine.component.mock;

import com.jusoft.bookingengine.component.booking.BookingHolder;
import com.jusoft.bookingengine.component.room.RoomHolder;
import com.jusoft.bookingengine.component.slot.SlotHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HolderConfig {

  @Bean
  public SlotHolder slotHolder() {
    return new SlotHolder();
  }

  @Bean
  public BookingHolder bookingHolder() {
    return new BookingHolder();
  }

  @Bean
  public RoomHolder roomHolder() {
    return new RoomHolder();
  }
}
