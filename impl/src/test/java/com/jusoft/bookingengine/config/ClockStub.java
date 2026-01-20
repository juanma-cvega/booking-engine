package com.jusoft.bookingengine.config;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class ClockStub extends Clock {

    private Clock clock;

    @Override
    public ZoneId getZone() {
        return clock.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return clock.withZone(zone);
    }

    @Override
    public Instant instant() {
        return clock.instant();
    }
}
