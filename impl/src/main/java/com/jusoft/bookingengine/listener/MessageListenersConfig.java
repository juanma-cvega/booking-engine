package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.scheduler.SchedulerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListenersConfig {

  @Autowired
  private RoomComponent roomComponent;
  @Autowired
  private AuctionComponent auctionComponent;
  @Autowired
  private SchedulerComponent schedulerComponent;
  @Autowired
  private BookingComponent bookingComponent;

  @Bean
  public OpenNextSlotCommandListener createSlotCommandListener() {
    return new OpenNextSlotCommandListener(roomComponent);
  }

  @Bean
  public SlotCreatedEventListener slotCreatedEventListener() {
    return new SlotCreatedEventListener(roomComponent, auctionComponent);
  }

  @Bean
  public RoomCreatedEventListener roomCreatedEventListener() {
    return new RoomCreatedEventListener(roomComponent);
  }

  @Bean
  public AuctionFinishedEventListener auctionFinishedEventListener() {
    return new AuctionFinishedEventListener(auctionComponent);
  }

  @Bean
  public AuctionStartedEventListener auctionStartedEventListener() {
    return new AuctionStartedEventListener(schedulerComponent);
  }

  @Bean
  public AuctionWinnerFoundEventListener auctionWinnerFoundEventListener() {
    return new AuctionWinnerFoundEventListener(bookingComponent);
  }
}
