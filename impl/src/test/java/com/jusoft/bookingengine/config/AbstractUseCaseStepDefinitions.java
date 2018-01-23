package com.jusoft.bookingengine.config;

import com.jusoft.bookingengine.MainConfig;
import com.jusoft.bookingengine.publisher.Message;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import cucumber.api.java8.En;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;

@ContextConfiguration(classes = {
  MainConfig.class,
  MockConfig.class
})
@DirtiesContext
public class AbstractUseCaseStepDefinitions implements En {

  @Autowired
  protected MessagePublisher messagePublisher;
  @Autowired
  protected ClockStub clock;

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
      exceptionThrown = exception;
    }
  }
}
