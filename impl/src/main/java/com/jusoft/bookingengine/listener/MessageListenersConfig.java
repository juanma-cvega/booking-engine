package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.component.scheduler.api.SchedulerComponent;
import com.jusoft.bookingengine.usecase.AuctionUseCase;
import com.jusoft.bookingengine.usecase.BookingUseCase;
import com.jusoft.bookingengine.usecase.SlotUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListenersConfig {

  @Autowired
  private SlotUseCase slotUseCase;
  @Autowired
  private AuctionUseCase auctionUseCase;
  @Autowired
  private BookingUseCase bookingUseCase;
  @Autowired
  private SchedulerComponent schedulerComponent;

  @Bean
  public OpenNextSlotCommandListener createSlotCommandListener() {
    return new OpenNextSlotCommandListener(slotUseCase);
  }

  @Bean
  public SlotCreatedEventListener slotCreatedEventListener() {
    return new SlotCreatedEventListener(slotUseCase, auctionUseCase);
  }

  @Bean
  public RoomCreatedEventListener roomCreatedEventListener() {
    return new RoomCreatedEventListener(slotUseCase);
  }

  @Bean
  public AuctionFinishedEventListener auctionFinishedEventListener() {
    return new AuctionFinishedEventListener(auctionUseCase);
  }

  @Bean
  public AuctionWinnerFoundEventListener auctionWinnerFoundEventListener() {
    return new AuctionWinnerFoundEventListener(bookingUseCase);
  }

  @Bean
  public SchedulerEventListener schedulerEventListener() {
    return new SchedulerEventListener(schedulerComponent);
  }
}
