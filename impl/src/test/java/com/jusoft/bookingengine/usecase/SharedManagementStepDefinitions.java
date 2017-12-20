package com.jusoft.bookingengine.usecase;

import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.reset;

public class SharedManagementStepDefinitions extends AbstractUseCaseStepDefinitions {

  public SharedManagementStepDefinitions() {
    Given("^current time is (.*)$", (String currentTime) ->
      clock.setClock(getFixedClockAt(currentTime)));
    Given("^that sets the background$", () ->
      reset(messagePublisher));
  }

  private Clock getFixedClockAt(String localTime) {
    ZonedDateTime currentDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.parse(localTime), clock.getZone());
    return Clock.fixed(currentDateTime.toInstant(), currentDateTime.getZone());
  }

}
