package com.jusoft.bookingengine.usecase;

import static java.time.ZonedDateTime.now;
import static org.mockito.Mockito.reset;

import com.jusoft.bookingengine.config.AbstractUseCaseStepDefinitions;
import io.cucumber.java.en.Given;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SharedStepDefinitions extends AbstractUseCaseStepDefinitions {

    @Given("^current time is (.*)$")
    public void current_time_is(String currentTime) {
        clock.setClock(getFixedClockAtTime(currentTime));
    }

    @Given("^current date time is (.*)$")
    public void current_date_time_is(String currentDateTime) {
        clock.setClock(getFixedClockAtDateTime(currentDateTime));
    }

    @Given("^current date time has moved by (\\d+) (.*)$")
    public void current_date_time_has_moved_by(Long value, ChronoUnit unit) {
        clock.setClock(getFixedClockAtDateTime(now(clock).plus(value, unit)));
    }

    @Given("^that sets the background$")
    public void that_sets_the_background() {
        reset(messagePublisher);
    }

    private Clock getFixedClockAtTime(String localTime) {
        ZonedDateTime currentDateTime =
                ZonedDateTime.of(LocalDate.now(), LocalTime.parse(localTime), clock.getZone());
        return Clock.fixed(currentDateTime.toInstant(), currentDateTime.getZone());
    }

    private Clock getFixedClockAtDateTime(String date) {
        LocalDateTime dateTime =
                LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm"));
        ZonedDateTime currentDateTime = dateTime.atZone(clock.getZone());
        return getFixedClockAtDateTime(currentDateTime);
    }

    private Clock getFixedClockAtDateTime(ZonedDateTime date) {
        return Clock.fixed(date.toInstant(), date.getZone());
    }
}
