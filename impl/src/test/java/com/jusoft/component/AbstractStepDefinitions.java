package com.jusoft.component;

import com.jusoft.component.booking.BookingComponentConfig;
import com.jusoft.component.mock.MockConfig;
import com.jusoft.component.room.RoomComponentConfig;
import com.jusoft.component.scheduler.SchedulerComponentConfig;
import com.jusoft.component.slot.SlotComponentConfig;
import com.jusoft.component.timer.TimerConfig;
import com.jusoft.listener.MessageListenersConfig;
import com.jusoft.publisher.MessagePublisherConfig;
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
        HolderConfig.class,
        MockConfig.class})
@DirtiesContext
public class AbstractStepDefinitions implements En {
}
