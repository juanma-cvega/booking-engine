package com.jusoft.component;

import com.jusoft.component.booking.BookingComponentConfig;
import com.jusoft.component.timer.TimerConfig;
import com.jusoft.component.slot.SlotComponentConfig;
import com.jusoft.component.mock.MockConfig;
import cucumber.api.java8.En;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {TimerConfig.class, BookingComponentConfig.class, SlotComponentConfig.class, HolderConfig.class, MockConfig.class})
@DirtiesContext
public class AbstractStepDefinitions implements En {
}
