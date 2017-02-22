package com.jusoft;

import com.jusoft.booking.BookingComponentConfig;
import com.jusoft.mock.MockConfig;
import com.jusoft.shared.SharedConfig;
import com.jusoft.slot.SlotComponentConfig;
import cucumber.api.java8.En;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {SharedConfig.class, BookingComponentConfig.class, SlotComponentConfig.class, HolderConfig.class, MockConfig.class})
@DirtiesContext
public class AbstractStepDefinitions implements En {
}
