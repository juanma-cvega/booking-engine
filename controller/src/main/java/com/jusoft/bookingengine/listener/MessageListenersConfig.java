package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.usecase.AddBuyerToAuctionUseCase;
import com.jusoft.bookingengine.usecase.CancelBookingUseCase;
import com.jusoft.bookingengine.usecase.CreateBookingUseCase;
import com.jusoft.bookingengine.usecase.CreateRoomUseCase;
import com.jusoft.bookingengine.usecase.FinishAuctionUseCase;
import com.jusoft.bookingengine.usecase.OpenNextSlotUseCase;
import com.jusoft.bookingengine.usecase.ScheduleNextSlotUseCase;
import com.jusoft.bookingengine.usecase.StartAuctionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListenersConfig {

  @Autowired
  private AddBuyerToAuctionUseCase addBuyerToAuctionUseCase;
  @Autowired
  private CancelBookingUseCase cancelBookingUseCase;
  @Autowired
  private CreateBookingUseCase createBookingUseCase;
  @Autowired
  private CreateRoomUseCase createRoomUseCase;
  @Autowired
  private FinishAuctionUseCase finishAuctionUseCase;
  @Autowired
  private OpenNextSlotUseCase openNextSlotUseCase;
  @Autowired
  private ScheduleNextSlotUseCase scheduleNextSlotUseCase;
  @Autowired
  private StartAuctionUseCase startAuctionUseCase;

  @Bean
  public OpenNextSlotCommandListener createSlotCommandListener() {
    return new OpenNextSlotCommandListener(openNextSlotUseCase);
  }

  @Bean
  public SlotCreatedEventListener slotCreatedEventListener() {
    return new SlotCreatedEventListener(scheduleNextSlotUseCase, startAuctionUseCase);
  }

  @Bean
  public RoomCreatedEventListener roomCreatedEventListener() {
    return new RoomCreatedEventListener(openNextSlotUseCase);
  }

  @Bean
  public AuctionFinishedEventListener auctionFinishedEventListener() {
    return new AuctionFinishedEventListener(finishAuctionUseCase);
  }

  @Bean
  public AuctionWinnerFoundEventListener auctionWinnerFoundEventListener() {
    return new AuctionWinnerFoundEventListener(createBookingUseCase);
  }

  @Bean
  public BookingCreatedEventListener bookingCreatedEventListener() {
    return new BookingCreatedEventListener();
  }
}
