package com.jusoft.bookingengine.listener;

import com.jusoft.bookingengine.usecase.auction.AddBidderToAuctionUseCase;
import com.jusoft.bookingengine.usecase.auction.FinishAuctionUseCase;
import com.jusoft.bookingengine.usecase.auction.StartAuctionUseCase;
import com.jusoft.bookingengine.usecase.booking.CancelBookingUseCase;
import com.jusoft.bookingengine.usecase.booking.CreateBookingUseCase;
import com.jusoft.bookingengine.usecase.room.CreateRoomUseCase;
import com.jusoft.bookingengine.usecase.slot.CreateSlotUseCase;
import com.jusoft.bookingengine.usecase.slot.ReserveSlotForAuctionWinnerUseCase;
import com.jusoft.bookingengine.usecase.slot.ScheduleNextSlotUseCase;
import com.jusoft.bookingengine.usecase.slotlifecycle.FindNextSlotStateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListenersConfig {

  @Autowired
  private AddBidderToAuctionUseCase addBidderToAuctionUseCase;
  @Autowired
  private CancelBookingUseCase cancelBookingUseCase;
  @Autowired
  private CreateBookingUseCase createBookingUseCase;
  @Autowired
  private CreateRoomUseCase createRoomUseCase;
  @Autowired
  private FinishAuctionUseCase finishAuctionUseCase;
  @Autowired
  private CreateSlotUseCase openNextSlotUseCase;
  @Autowired
  private ScheduleNextSlotUseCase scheduleNextSlotUseCase;
  @Autowired
  private StartAuctionUseCase startAuctionUseCase;
  @Autowired
  private ReserveSlotForAuctionWinnerUseCase reserveSlotForAuctionWinnerUseCase;
  @Autowired
  private FindNextSlotStateUseCase findNextSlotStateUseCase;

  @Bean
  public SlotRequiredEventListener createSlotCommandListener() {
    return new SlotRequiredEventListener(openNextSlotUseCase);
  }

  @Bean
  public SlotCreatedEventListener slotCreatedEventListener() {
    return new SlotCreatedEventListener(scheduleNextSlotUseCase, findNextSlotStateUseCase);
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
    return new AuctionWinnerFoundEventListener(reserveSlotForAuctionWinnerUseCase);
  }

  @Bean
  public BookingCreatedEventListener bookingCreatedEventListener() {
    return new BookingCreatedEventListener();
  }
}
