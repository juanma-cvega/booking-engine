package com.jusoft.bookingengine.component;

import com.jusoft.bookingengine.component.auction.AuctionComponentConfig;
import com.jusoft.bookingengine.component.booking.BookingComponentConfig;
import com.jusoft.bookingengine.component.mock.ClockStub;
import com.jusoft.bookingengine.component.mock.MessagesSink;
import com.jusoft.bookingengine.component.mock.MessagesSinkConfig;
import com.jusoft.bookingengine.component.mock.ScheduledTasksExecutor;
import com.jusoft.bookingengine.component.mock.ScheduledTasksExecutorConfig;
import com.jusoft.bookingengine.component.mock.TimerMockConfig;
import com.jusoft.bookingengine.component.room.RoomComponentConfig;
import com.jusoft.bookingengine.component.scheduler.SchedulerComponentConfig;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import com.jusoft.bookingengine.component.slot.SlotComponentConfig;
import com.jusoft.bookingengine.component.timer.TimerConfig;
import com.jusoft.bookingengine.listener.MessageListenersConfig;
import com.jusoft.bookingengine.publisher.MessagePublisherConfig;
import com.jusoft.bookingengine.usecase.UseCaseConfig;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import static com.jusoft.bookingengine.component.timer.TimeConstants.UTC;

@ContextConfiguration(classes = {
  TimerConfig.class,
  TimerMockConfig.class,
  MessagePublisherConfig.class,
  SchedulerComponentConfig.class,
  BookingComponentConfig.class,
  SlotComponentConfig.class,
  RoomComponentConfig.class,
  AuctionComponentConfig.class,
  UseCaseConfig.class,
  MessageListenersConfig.class,
  HolderConfig.class,
  ScheduledTasksExecutorConfig.class,
  MessagesSinkConfig.class})
@DirtiesContext
public class AbstractStepDefinitions implements En {

  @Autowired
  protected ScheduledTasksExecutor scheduledTasksExecutor;

  @Autowired
  protected ClockStub clock;

  @Autowired
  protected MessagesSink messagesSink;

  @Autowired
  protected MessagePublisher messagePublisher;

  protected Clock getFixedClockAt(String localTime) {
    LocalDateTime currentDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(localTime));
    return Clock.fixed(currentDateTime.toInstant(ZoneOffset.UTC), UTC);
  }
}
