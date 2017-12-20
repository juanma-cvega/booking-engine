package com.jusoft.bookingengine.config;

import com.jusoft.bookingengine.MainConfig;
import com.jusoft.bookingengine.component.shared.Message;
import com.jusoft.bookingengine.component.shared.MessagePublisher;
import com.jusoft.bookingengine.holder.AuctionHolder;
import com.jusoft.bookingengine.holder.BookingHolder;
import com.jusoft.bookingengine.holder.ExceptionHolder;
import com.jusoft.bookingengine.holder.RoomHolder;
import com.jusoft.bookingengine.holder.SlotHolder;
import cucumber.api.java8.En;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@ContextConfiguration(classes = {
  MainConfig.class,
  MockConfig.class,
  HolderConfig.class
})
@DirtiesContext
public class AbstractUseCaseStepDefinitions implements En {

  @Autowired
  protected MessagePublisher messagePublisher;
  @Autowired
  protected ClockStub clock;
  @Autowired
  protected ExceptionHolder exceptionHolder;
  @Autowired
  protected AuctionHolder auctionHolder;
  @Autowired
  protected RoomHolder roomHolder;
  @Autowired
  protected SlotHolder slotHolder;
  @Autowired
  protected BookingHolder bookingHolder;

  protected ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);

  protected ZonedDateTime getDateFrom(String time) {
    return getDateFrom(time, LocalDate.now(clock));
  }

  protected ZonedDateTime getDateFrom(String time, LocalDate date) {
    return ZonedDateTime.of(date, LocalTime.parse(time), clock.getZone());
  }

  protected void storeException(Runnable runnable) {
    try {
      runnable.run();
    } catch (RuntimeException exception) {
      exceptionHolder.exceptionThrown = exception;
    }
  }
}