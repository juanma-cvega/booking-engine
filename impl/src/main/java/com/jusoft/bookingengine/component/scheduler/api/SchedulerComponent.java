package com.jusoft.bookingengine.component.scheduler.api;

import com.jusoft.bookingengine.publisher.Message;
import java.time.ZonedDateTime;

public interface SchedulerComponent {

    void schedule(ZonedDateTime executionTime, Message scheduledEvent);
}
