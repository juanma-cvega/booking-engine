package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.component.auction.api.AuctionComponent;
import com.jusoft.bookingengine.component.booking.api.BookingComponent;
import com.jusoft.bookingengine.component.room.api.RoomComponent;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import com.jusoft.bookingengine.component.slot.api.SlotComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class UseCaseConfig {

  @Autowired
  private AuctionComponent auctionComponent;
  @Autowired
  private RoomComponent roomComponent;
  @Autowired
  private SlotComponent slotComponent;
  @Autowired
  private BookingComponent bookingComponent;
  @Autowired
  private Clock clock;
  @Autowired
  private MessagePublisher messagePublisher;

  @Bean
  public AuctionUseCase auctionUseCase() {
    return new AuctionUseCase(roomComponent, auctionComponent, messagePublisher);
  }

  @Bean
  public BookingUseCase bookingUseCase() {
    return new BookingUseCase(bookingComponent, slotComponent, auctionComponent);
  }

  @Bean
  public RoomUseCase createRoomUseCase() {
    return new RoomUseCase(roomComponent);
  }

  @Bean
  public SlotUseCase slotUseCase() {
    return new SlotUseCase(roomComponent, slotComponent, messagePublisher, clock);
  }
}
