package com.jusoft.bookingengine.config;

import com.jusoft.bookingengine.MainConfig;
import com.jusoft.bookingengine.publisher.Message;
import com.jusoft.bookingengine.publisher.MessagePublisher;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.jusoft.bookingengine.holder.DataHolder.exceptionThrown;
import static java.time.LocalTime.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@CucumberContextConfiguration
@SpringBootTest(classes = { MainConfig.class, MockConfig.class }, properties = "spring.main.allow-bean-definition-overriding=true")
@DirtiesContext
public class AbstractUseCaseStepDefinitions {

  @Autowired
  protected MessagePublisher messagePublisher;
  @Autowired
  protected ClockStub clock;

  protected ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);

  @SuppressWarnings("unchecked")
  protected <T extends Message> T verifyAndGetMessageOfType(Class<T> type) {
    verify(messagePublisher).publish(messageCaptor.capture());
    assertThat(messageCaptor.getValue()).isInstanceOf(type);
    return (T) messageCaptor.getValue();
  }

  @SuppressWarnings("unchecked")
  protected <T extends RuntimeException> T verifyAndGetExceptionThrown(Class<T> type) {
    assertThat(exceptionThrown).isInstanceOf(type);
    return (T) exceptionThrown;
  }

  protected ZonedDateTime getDateFrom(String time) {
    return getDateFrom(time, LocalDate.now(clock));
  }

  protected ZonedDateTime getDateFrom(String time, LocalDate date) {
    return ZonedDateTime.of(date, parse(time), clock.getZone());
  }

  protected ZonedDateTime getDateFrom(String time, String date) {
    return ZonedDateTime.of(LocalDate.parse(date), parse(time), clock.getZone());
  }

  protected ZonedDateTime getNextDateFrom(String time, DayOfWeek dayOfWeek, String zoneId) {
    return ZonedDateTime.of(LocalDate.now(clock).plusWeeks(1).with(dayOfWeek), parse(time), ZoneId.of(zoneId));
  }

  protected void storeException(Runnable runnable) {
    try {
      runnable.run();
    } catch (RuntimeException exception) {
      exceptionThrown = exception;
    }
  }
}
