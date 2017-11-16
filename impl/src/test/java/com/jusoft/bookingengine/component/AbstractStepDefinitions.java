package com.jusoft.bookingengine.component;

import com.jusoft.bookingengine.component.auction.AuctionComponentConfig;
import com.jusoft.bookingengine.component.booking.BookingComponentConfig;
import com.jusoft.bookingengine.component.mock.MockConfig;
import com.jusoft.bookingengine.component.room.RoomComponentConfig;
import com.jusoft.bookingengine.component.scheduler.SchedulerComponentConfig;
import com.jusoft.bookingengine.component.slot.SlotComponentConfig;
import com.jusoft.bookingengine.component.timer.TimerConfig;
import com.jusoft.bookingengine.listener.MessageListenersConfig;
import com.jusoft.bookingengine.publisher.MessagePublisherConfig;
import cucumber.api.java8.En;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {
  TimerConfig.class,
  MessageListenersConfig.class,
  MessagePublisherConfig.class,
  SchedulerComponentConfig.class,
  BookingComponentConfig.class,
  SlotComponentConfig.class,
  RoomComponentConfig.class,
  AuctionComponentConfig.class,
  HolderConfig.class,
  MockConfig.class})
@DirtiesContext
public class AbstractStepDefinitions implements En {
}
