package com.jusoft.bookingengine.publisher.factory;

import com.jusoft.bookingengine.component.auction.api.AuctionFinishedEvent;
import com.jusoft.bookingengine.component.auction.api.AuctionWinnerFoundEvent;
import com.jusoft.bookingengine.component.booking.api.BookingCreatedEvent;
import com.jusoft.bookingengine.component.room.api.OpenNextSlotCommand;
import com.jusoft.bookingengine.component.room.api.RoomCreatedEvent;
import com.jusoft.bookingengine.component.slot.api.SlotCreatedEvent;
import com.jusoft.bookingengine.publisher.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class InfrastructureMessageFactoryConfig {

  @Bean
  public InfrastructureMessageFactory infrastructureMessageFactory() {
    return new InfrastructureMessageFactory(createFactories());
  }

  private Map<Class<? extends Message>, MessageFactory> createFactories() {
    Map<Class<? extends Message>, MessageFactory> factories = new HashMap<>();

    factories.put(AuctionFinishedEvent.class, new AuctionFinishedMessageFactory());
    factories.put(AuctionWinnerFoundEvent.class, new AuctionWinnerFoundMessageFactory());
    factories.put(BookingCreatedEvent.class, new BookingCreatedMessageFactory());
    factories.put(OpenNextSlotCommand.class, new OpenNextSlotMessageFactory());
    factories.put(RoomCreatedEvent.class, new RoomCreatedMessageFactory());
    factories.put(SlotCreatedEvent.class, new SlotCreatedMessageFactory());
    return factories;
  }
}
