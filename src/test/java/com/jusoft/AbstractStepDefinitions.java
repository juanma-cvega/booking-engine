package com.jusoft;

import com.jusoft.booking.BookingComponentConfig;
import com.jusoft.holder.HolderConfig;
import com.jusoft.slot.SlotComponentConfig;
import cucumber.api.java8.En;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {BookingComponentConfig.class, SlotComponentConfig.class, HolderConfig.class})
@DirtiesContext
public class AbstractStepDefinitions implements En {
}
