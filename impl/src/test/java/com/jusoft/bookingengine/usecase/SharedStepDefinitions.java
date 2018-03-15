package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.reset;

public class SharedStepDefinitions extends AbstractUseCaseStepDefinitions {

  public SharedStepDefinitions() {
    Given("^current time is (.*)$", (String currentTime) ->
      clock.setClock(getFixedClockAtTime(currentTime)));
    Given("^current date time is (.*)$", (String currentDateTime) ->
      clock.setClock(getFixedClockAtDateTime(currentDateTime)));
    Given("^that sets the background$", () ->
      reset(messagePublisher));
  }

  private Clock getFixedClockAtTime(String localTime) {
    ZonedDateTime currentDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.parse(localTime), clock.getZone());
    return Clock.fixed(currentDateTime.toInstant(), currentDateTime.getZone());
  }

  private Clock getFixedClockAtDateTime(String date) {
    LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm"));
    ZonedDateTime currentDateTime = dateTime.atZone(clock.getZone());
    return Clock.fixed(currentDateTime.toInstant(), currentDateTime.getZone());
  }

}
